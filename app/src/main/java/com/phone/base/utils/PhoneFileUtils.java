package com.phone.base.utils;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class PhoneFileUtils {
    public static final String DIR_NAME = "Download/PhoneTest/";
    public static final String FILE_NAME = "MyPhoneInfo.txt";

    public static void listAndDeleteFiles(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            String queryPathKey = MediaStore.Files.FileColumns.RELATIVE_PATH;

            //delete target file name file,but no need here
/*            String selection = queryPathKey + "=? and " + MediaStore.Files.FileColumns.DISPLAY_NAME + "=?";
            Cursor cursor = context.getContentResolver().query(MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL),
                    new String[]{MediaStore.Files.FileColumns._ID, queryPathKey, MediaStore.Files.FileColumns.DISPLAY_NAME},
                    selection,
                    new String[]{DIR_NAME, FILE_NAME},
                    null); */

            String selection = queryPathKey + "=?";
            Cursor cursor = context.getContentResolver().query(MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL),
                    new String[]{MediaStore.Files.FileColumns._ID, queryPathKey},
                    selection,
                    new String[]{DIR_NAME},
                    null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    //媒体数据库中查询到的文件id
                    int columnId = cursor.getColumnIndex(MediaStore.Images.Media._ID);
                    do {
                        //通过mediaId获取它的uri
                        int mediaId = cursor.getInt(columnId);
                        Uri itemUri = null;
                        itemUri = Uri.withAppendedPath(MediaStore.Downloads.EXTERNAL_CONTENT_URI, "" + mediaId);
                        //通过uri获取到inputStream
                        ContentResolver cr = context.getContentResolver();
                        cr.delete(itemUri, null, null);

                    } while (cursor.moveToNext());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static void initPublicFileToAppFile(Context context, String targetPath) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            String queryPathKey = null;

            queryPathKey = MediaStore.Files.FileColumns.RELATIVE_PATH;

            String selection = queryPathKey + "=? and " + MediaStore.Files.FileColumns.DISPLAY_NAME + "=?";
            Cursor cursor = context.getContentResolver().query(MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL),
                    new String[]{MediaStore.Files.FileColumns._ID, queryPathKey, MediaStore.Files.FileColumns.DISPLAY_NAME},
                    selection,
                    new String[]{DIR_NAME, FILE_NAME},
                    null);


            OutputStream ost = null;
            try {
                ost = new FileOutputStream(new File(targetPath));
                if (cursor != null && cursor.moveToFirst()) {
                    //媒体数据库中查询到的文件id
                    int columnId = cursor.getColumnIndex(MediaStore.Images.Media._ID);
                    do {
                        //通过mediaId获取它的uri
                        int mediaId = cursor.getInt(columnId);
                        Uri itemUri = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                            itemUri = Uri.withAppendedPath(MediaStore.Downloads.EXTERNAL_CONTENT_URI, "" + mediaId);
                        }
                        try {
                            //通过uri获取到inputStream
                            ContentResolver cr = context.getContentResolver();
                            InputStream ins = cr.openInputStream(itemUri);
//                            insList.add(ins);

                            if (ost != null) {
                                byte[] buffer = new byte[4096];
                                int byteCount = 0;
                                while ((byteCount = ins.read(buffer)) != -1) {  // 循环从输入流读取 buffer字节
                                    ost.write(buffer, 0, byteCount);        // 将读取的输入流写入到输出流
                                }
                                // write what you want
                            }

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                    } while (cursor.moveToNext());
                }

            } catch (IOException e) {
                //Log.i("copyPrivateToDownload--","fail: " + e.getCause());
            } finally {
                try {

                    if (ost != null) {
                        ost.close();
                    }
                } catch (IOException e) {
                    //Log.i("copyPrivateToDownload--","fail in close: " + e.getCause());
                }
            }
        } else {
            //android 9以下
            String stringResult = FileSystem.readString(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), FILE_NAME));
            if (!TextUtils.isEmpty(stringResult)) {
                FileSystem.writeString(context.getFilesDir(), FILE_NAME, stringResult);
            }
        }
    }


    public static void copyPrivateToDocuments(Context context, String orgFilePath) {
        listAndDeleteFiles(context);
        ContentValues values = new ContentValues();
        //values.put(MediaStore.Images.Media.DESCRIPTION, "This is a file");
        values.put(MediaStore.Files.FileColumns.DISPLAY_NAME, FILE_NAME);
        values.put(MediaStore.Files.FileColumns.MIME_TYPE, "text/plain");//MediaStore对应类型名
        values.put(MediaStore.Files.FileColumns.TITLE, FILE_NAME);
        values.put(MediaStore.Images.Media.RELATIVE_PATH, DIR_NAME);//公共目录下目录名

        Uri external = null;//内部存储的Download路径
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            external = MediaStore.Downloads.EXTERNAL_CONTENT_URI;
        }
        ContentResolver resolver = context.getContentResolver();

        Uri insertUri = resolver.insert(external, values);//使用ContentResolver创建需要操作的文件
        //Log.i("Test--","insertUri: " + insertUri);

        InputStream ist = null;
        OutputStream ost = null;
        try {
            ist = new FileInputStream(new File(orgFilePath));
            if (insertUri != null) {
                ost = resolver.openOutputStream(insertUri);
            }
            if (ost != null) {
                byte[] buffer = new byte[4096];
                int byteCount = 0;
                while ((byteCount = ist.read(buffer)) != -1) {  // 循环从输入流读取 buffer字节
                    ost.write(buffer, 0, byteCount);        // 将读取的输入流写入到输出流
                }
                // write what you want
            }
        } catch (IOException e) {
            //Log.i("copyPrivateToDownload--","fail: " + e.getCause());
        } finally {
            try {
                if (ist != null) {
                    ist.close();
                }
                if (ost != null) {
                    ost.close();
                }
            } catch (IOException e) {
                //Log.i("copyPrivateToDownload--","fail in close: " + e.getCause());
            }
        }

    }

    //在公共文件夹下查询图片
//这里的filepath在androidQ中表示相对路径
//在androidQ以下是绝对路径
    public static void querySignImage(Context context, String name, String targetPath) {
        String filePath = "Download/PhoneTest/";
        String queryPathKey = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {

            queryPathKey = MediaStore.Files.FileColumns.RELATIVE_PATH;

            String selection = queryPathKey + "=? and " + MediaStore.Files.FileColumns.DISPLAY_NAME + "=?";
            Cursor cursor = context.getContentResolver().query(MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL),
                    new String[]{MediaStore.Files.FileColumns._ID, queryPathKey, MediaStore.Files.FileColumns.DISPLAY_NAME},
                    selection,
                    new String[]{filePath, name},
                    null);


            OutputStream ost = null;
            try {
                ost = new FileOutputStream(new File(targetPath));
                if (cursor != null && cursor.moveToFirst()) {
                    //媒体数据库中查询到的文件id
                    int columnId = cursor.getColumnIndex(MediaStore.Images.Media._ID);
                    do {
                        //通过mediaId获取它的uri
                        int mediaId = cursor.getInt(columnId);
                        Uri itemUri = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                            itemUri = Uri.withAppendedPath(MediaStore.Downloads.EXTERNAL_CONTENT_URI, "" + mediaId);
                        }
                        try {
                            //通过uri获取到inputStream
                            ContentResolver cr = context.getContentResolver();
                            InputStream ins = cr.openInputStream(itemUri);
//                            insList.add(ins);

                            if (ost != null) {
                                byte[] buffer = new byte[4096];
                                int byteCount = 0;
                                while ((byteCount = ins.read(buffer)) != -1) {  // 循环从输入流读取 buffer字节
                                    ost.write(buffer, 0, byteCount);        // 将读取的输入流写入到输出流
                                }
                                // write what you want
                            }

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                    } while (cursor.moveToNext());
                }

            } catch (IOException e) {
                //Log.i("copyPrivateToDownload--","fail: " + e.getCause());
            } finally {
                try {

                    if (ost != null) {
                        ost.close();
                    }
                } catch (IOException e) {
                    //Log.i("copyPrivateToDownload--","fail in close: " + e.getCause());
                }
            }
        }
//        return null;
    }

}
