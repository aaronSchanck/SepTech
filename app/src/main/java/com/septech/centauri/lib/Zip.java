package com.septech.centauri.lib;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;
import retrofit2.Response;

import static com.septech.centauri.persistent.CentauriApp.getAppContext;

/**
 * Utility class to convert ZIP files into their respective contents. Methods should be called by
 * using flatmap(method) on an Observer.
 */
public class Zip {
    public static Function<Response<ResponseBody>, Observable<File>> processResponse() {
        return Zip::saveToDisk;
    }

    private static Observable<File> saveToDisk(final Response<ResponseBody> response) {
        return Observable.create(emitter -> {
            try {
                String header = response.headers().get("Content-Disposition");
                String filename = header.replace("attachment; filename=", "");

                File path = getAppContext().getExternalCacheDir();

                path.mkdir();

                File file = new File(path, filename);

                BufferedSink bufferedSink = Okio.buffer(Okio.sink(file));

                bufferedSink.writeAll(response.body().source());
                bufferedSink.close();

                response.body().close();

                emitter.onNext(file);
            } catch (IOException e) {
                Log.i("Error", e.toString());
                emitter.onError(e);
            }
        });
    }

    public static Function<File, Observable<Map<Integer, Uri>>> unpackZipThumbnails() {
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

            if (!file.delete()) Log.w("unpackZip", "Failed to delete the zip file.");
            return Observable.just(uris);
        };
    }

    public static Function<File, Observable<List<Uri>>> unpackZipImages() {
        return file -> {
            InputStream is;
            ZipInputStream zis;

            String parentFolder;
            String filename;

            List<Uri> uris = new ArrayList<>();
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

                    uris.add(Uri.fromFile(fileEntry));
                }

                zis.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

            if (!file.delete()) Log.w("unpackZip", "Failed to delete the zip file.");
            return Observable.just(uris);
        };
    }
}
