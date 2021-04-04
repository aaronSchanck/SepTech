package com.septech.centauri.data.repository;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.septech.centauri.data.cache.FileCache;
import com.septech.centauri.data.db.betelgeuse.BetelgeuseDatabase;
import com.septech.centauri.data.model.item.ItemEntity;
import com.septech.centauri.data.model.item.mapper.ItemDataMapper;
import com.septech.centauri.data.net.RestApiClient;
import com.septech.centauri.domain.models.Item;
import com.septech.centauri.domain.repository.ItemRepository;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ItemDataRepository implements ItemRepository {
    private static final String TAG = UserDataRepository.class.getSimpleName();

    private static ItemDataRepository mInstance;

    private BetelgeuseDatabase database;
    private final FileCache fileCache;
    private final RestApiClient restApiImpl;
    private final BetelgeuseDatabase localDb;

    private ItemDataRepository() {
        this.restApiImpl = RestApiClient.getInstance();
        this.localDb = BetelgeuseDatabase.getDatabase();
        this.fileCache = new FileCache();
    }

    public static ItemDataRepository getInstance() {
        if (mInstance == null) {
            mInstance = new ItemDataRepository();
        }
        return mInstance;
    }


    public Single<ItemEntity> createItem(String imagePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        try {
            // Read BitMap by file path.
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        } catch(Exception e) {
            e.printStackTrace();
        }

        byte[] byteArray = stream.toByteArray();

        MultipartBody.Part image = MultipartBody.Part.createFormData("image",
                "Android_Flask_" + 1 +
                        ".jpg",
                RequestBody.create(byteArray, MediaType.parse("image/*jpg")));

        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setDescription("some words");

        return null;
//        return restApiImpl.createItem(image, itemEntity);
    }

    @Override
    public Single<Item> createItem(Item item, List<String> imagePaths) {
        ItemEntity itemEntity = ItemDataMapper.transform(item);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        List<MultipartBody.Part> body = new ArrayList<>();

        for (int i = 0; i < imagePaths.size(); i++) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            try {
                // read BitMap by file path.
                Bitmap bitmap = BitmapFactory.decodeFile(imagePaths.get(i), options);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            } catch (Exception e) {
                e.printStackTrace();
            }

            byte[] byteArray = stream.toByteArray();

            MultipartBody.Part image = MultipartBody.Part.createFormData("image_" + i,
                    "images_" + i +
                            ".jpg",
                    RequestBody.create(byteArray, MediaType.parse("image/*jpg")));

            body.add(image);
        }

        return restApiImpl.createItem(body, itemEntity).map(ItemDataMapper::transform);
    }
    
    @Override
    public Observable<List<Item>> searchItems(String query) {
        return null;
    }
}
