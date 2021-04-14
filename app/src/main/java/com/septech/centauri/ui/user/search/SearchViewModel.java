package com.septech.centauri.ui.user.search;

import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.septech.centauri.data.repository.ItemDataRepository;
import com.septech.centauri.domain.models.Item;
import com.septech.centauri.domain.repository.ItemRepository;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;
import retrofit2.Response;

import static com.septech.centauri.persistent.CentauriApp.getAppContext;

public class SearchViewModel extends ViewModel {
    private static final String TAG = "SearchViewModel";

    private ItemRepository itemRepo;

    private MutableLiveData<List<Item>> itemsLiveData = new MutableLiveData<>();
    private MutableLiveData<Map<Integer, Uri>> imagesLiveData = new MutableLiveData<>();
    private Integer currentPage;
    private MutableLiveData<Integer> searchAmount = new MutableLiveData<>();
    private String currentQuery;

    private final CompositeDisposable mDisposables = new CompositeDisposable();

    public SearchViewModel() {
        itemRepo = ItemDataRepository.getInstance();

        currentPage = 0;
    }

    public void search() {
        mDisposables.add(itemRepo.getAmountInQuery(currentQuery)
                .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(new DisposableSingleObserver<Integer>() {
                        @Override
                        public void onSuccess(@NonNull Integer integer) {
                            Log.i(TAG, "Search amount: " + integer);

                            searchAmount.setValue(integer);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                        Log.i(TAG, "onError: " + e);
                    }
                }));

        mDisposables.add(itemRepo.searchItems(currentQuery, currentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Item>>() {
                    @Override
                    public void onNext(@NonNull List<Item> items) {
                        Log.i(TAG, "Items received: " + items);
                        itemsLiveData.setValue(items);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("e = " + e);
                    }

                    @Override
                    public void onComplete() {
                    }
                }));
    }

    public void newSearch(String query) {
        currentPage = 0;
        currentQuery = query;

        search();
    }

    public void lastPage() {
        currentPage -= 1;

        search();
    }

    public void nextPage() {
        currentPage += 1;

        search();
    }

    public void getImages(int[] itemIds) {
        mDisposables.add(itemRepo.getImagesZip(itemIds)
                .flatMap(processResponse())
                .retry(25) //must be used when running the emulator, don't ask. I don't know why
                .flatMap(unpackZip())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<File>() {
                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onCompleted");
                    }

                    @Override
                    public void onNext(@NonNull File file) {
                        Log.i(TAG, "Images received and converted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "Error in conversion: " + e);
                    }
                }));
    }

    private Function<Response<ResponseBody>, Observable<File>> processResponse() {
        return this::saveToDisk;
    }

    private Observable<File> saveToDisk(final Response<ResponseBody> response) {
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
                Log.i(TAG, e.toString());
                emitter.onError(e);
            }
        });
    }

    private Function<File, Observable<File>> unpackZip() {
        return file -> {
            InputStream is;
            ZipInputStream zis;

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
            if (!file.delete()) Log.w("unpackZip", "Failed to delete the zip file.");
            return Observable.just(extractedFile);
        };
    }

    public MutableLiveData<Integer> getSearchAmount() {
        return searchAmount;
    }

    public MutableLiveData<List<Item>> getItemsLiveData() {
        return itemsLiveData;
    }

    public MutableLiveData<Map<Integer, Uri>> getImagesLiveData() {
        return imagesLiveData;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public String getCurrentQuery() {
        return currentQuery;
    }
}
