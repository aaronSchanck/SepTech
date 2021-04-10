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

/**
 * An implementation of the domain-level ItemRepository. Will pull data from an arbitrary
 * location, mainly depending on whether the information exists within the faster levels of
 * acquisition. The level of checking is expected to be cache -> local database -> remote API.
 *
 * @author adamg
 * @version 1.0
 * @since 1.0
 */
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

    /**
     * Singleton accessor for the ItemDataRepository class
     *
     * @return The existing instance of the ItemDataRepository. If the instance doesn't exist, it
     * will return a new instance.
     */
    public static ItemDataRepository getInstance() {
        if (mInstance == null) {
            mInstance = new ItemDataRepository();
        }
        return mInstance;
    }

    /**
     * Creates an item in the remote database. Converts the incoming Item object with filled in
     * data components into an ItemEntity (remote), then grabs all of the ImagePaths and converts
     * them to corresponding MediaTypes to be sent as FileStorage objects to the Flask API.
     *
     * @param item       Item object to be created
     * @param imagePaths Paths of the images for the corresponding item
     * @return An observable Single of the Item object, returned from the remote API.
     */
    @Override
    public Single<Item> createItem(Item item, List<String> imagePaths) {
        ItemEntity itemEntity = ItemDataMapper.transform(item);

        //set options for BitMapFactory
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        List<MultipartBody.Part> body = new ArrayList<>();  //build the multipart for the imagepaths

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

            //naming scheme: images_i where i = 1-images.size()
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
