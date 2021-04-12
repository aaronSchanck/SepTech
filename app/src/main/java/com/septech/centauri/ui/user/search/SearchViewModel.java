package com.septech.centauri.ui.user.search;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.septech.centauri.data.repository.ItemDataRepository;
import com.septech.centauri.domain.models.Item;
import com.septech.centauri.domain.repository.ItemRepository;

import org.apache.commons.io.IOUtils;
import org.reactivestreams.Subscriber;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Function;
import okio.BufferedSink;
import okio.Okio;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static com.septech.centauri.persistent.CentauriApp.getAppContext;

public class SearchViewModel extends ViewModel {
    private static final String TAG = "SearchViewModel";

    private ItemRepository itemRepo;

    private MutableLiveData<List<Item>> itemsLiveData = new MutableLiveData<>();
    private MutableLiveData<Map<Integer, Uri>> imagesLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> currentPage = new MutableLiveData<>();

    private final CompositeDisposable mDisposables = new CompositeDisposable();

    public SearchViewModel() {
        itemRepo = ItemDataRepository.getInstance();
    }

    public void getItems(String query, int page) {
        currentPage.setValue(page);

        mDisposables.add(itemRepo.getAmountInQuery(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Integer>() {
                    @Override
                    public void onSuccess(@NonNull Integer integer) {
                        System.out.println("integer = " + integer);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("e = " + e);
                    }
                }));

        mDisposables.add(itemRepo.searchItems(query, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Item>>() {
                    @Override
                    public void onNext(@NonNull List<Item> items) {
                        System.out.println("items = " + items);
                        itemsLiveData.setValue(items);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("e = " + e);
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("words");
                    }
                }));
    }

    public void getImages(int[] itemIds) {
        mDisposables.add(itemRepo.getImagesZip(itemIds)
                .flatMap(processResponse())
                .retry(25)
                .flatMap(unpackZip())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<File>() {
                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onCompleted");
                    }

                    @Override
                    public void onNext(@NonNull File file) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.d(TAG, "Error " + e.getMessage());
                    }
                }));
    }

    private Function<Response<ResponseBody>, Observable<File>> processResponse() {
        return this::saveToDiskRx;
    }

    private Observable<File> saveToDiskRx(final Response<ResponseBody> response) {
        return Observable.create(emitter -> {
            try {
                String header = response.headers().get("Content-Disposition");
                String filename = header.replace("attachment; filename=", "");

                File path = getAppContext().getExternalCacheDir();

                path.mkdir();

                System.out.println(path);
                File file = new File(path, filename);

                BufferedSink bufferedSink = Okio.buffer(Okio.sink(file));

                System.out.println(response);
                bufferedSink.writeAll(response.body().source());
                bufferedSink.close();

                response.body().close();

                emitter.onNext(file);
            } catch (IOException e) {
                e.printStackTrace();
                emitter.onError(e);
            }
        });
    }

    private Function<File, Observable<File>> unpackZip() {
        return file -> {
            InputStream is;
            ZipInputStream zis;

            System.out.println("words3");

            String parentFolder;
            String filename;

            Map<Integer, Uri> uris = new HashMap<>();
            try {
                File extractedFile = new File(file.getAbsolutePath().replace(".zip", ""));
                extractedFile.mkdir();

                parentFolder = file.getParentFile().getPath();


                is = new FileInputStream(file.getAbsolutePath());
                zis = new ZipInputStream(new BufferedInputStream(is));
                ZipEntry ze;
                byte[] buffer = new byte[1024];
                int count;

                while ((ze = zis.getNextEntry()) != null) {
                    filename = ze.getName();

                    if (ze.isDirectory()) {
                        File fmd = new File(parentFolder + "/" + filename);
                        fmd.mkdirs();
                        continue;
                    }

                    File fileEntry = new File(parentFolder + "/" + filename);

                    FileOutputStream fout = new FileOutputStream(fileEntry);

                    while ((count = zis.read(buffer)) != -1) {
                        fout.write(buffer, 0, count);
                    }

                    fout.close();
                    zis.closeEntry();

                    int itemId = Integer.parseInt(filename.replace("thumbnails/thumbnail_", "").replace(".jpg",
                            ""));

                    uris.put(itemId, Uri.fromFile(fileEntry));
                }

                zis.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

            imagesLiveData.postValue(uris);

            File extractedFile = new File(file.getAbsolutePath().replace(".zip", ""));
            if (!file.delete()) Log.d("unpackZip", "Failed to delete the zip file.");
            return Observable.just(extractedFile);
        };
    }

    public MutableLiveData<List<Item>> getItemsLiveData() {
        return itemsLiveData;
    }

    public MutableLiveData<Map<Integer, Uri>> getImagesLiveData() {
        return imagesLiveData;
    }

    public MutableLiveData<Integer> getCurrentPage() {
        return currentPage;
    }
}
