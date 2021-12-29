package com.phone.base.utils;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

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
    public static void copyPrivateToDocuments(Context context, String orgFilePath, String displayName){
        ContentValues values = new ContentValues();
        //values.put(MediaStore.Images.Media.DESCRIPTION, "This is a file");
        values.put(MediaStore.Files.FileColumns.DISPLAY_NAME, displayName);
        values.put(MediaStore.Files.FileColumns.MIME_TYPE, "text/plain");//MediaStore对应类型名
        values.put(MediaStore.Files.FileColumns.TITLE, displayName);
        values.put(MediaStore.Images.Media.RELATIVE_PATH, "Download/PhoneTest");//公共目录下目录名

        Uri external = null;//内部存储的Download路径
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            external = MediaStore.Downloads.EXTERNAL_CONTENT_URI;
        }
        ContentResolver resolver = context.getContentResolver();

        Uri insertUri = resolver.insert(external, values);//使用ContentResolver创建需要操作的文件
        //Log.i("Test--","insertUri: " + insertUri);

        InputStream ist=null;
        OutputStream ost = null;
        try {
            ist=new FileInputStream(new File(orgFilePath));
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

    //name是文件名称，是MediaStore查找文件的条件之一
    public static List<InputStream> getImageIns(Context context, String name)  {

        List<InputStream> insList = new ArrayList<>();

        ContentResolver resolver = context.getContentResolver();

        String sortOrder = MediaStore.Downloads.DATE_MODIFIED + " DESC";//根据日期降序查询


        Cursor cursor = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            String selection = MediaStore.Downloads.BUCKET_DISPLAY_NAME + "='" + name + "'";   //查询条件 “显示名称为？”

            cursor = resolver.query(MediaStore.Downloads.EXTERNAL_CONTENT_URI, null, selection, null, sortOrder);
        }


        if (cursor != null && cursor.moveToFirst()) {
            //媒体数据库中查询到的文件id
            int columnId = cursor.getColumnIndex(MediaStore.Images.Media._ID);

            do {
                //通过mediaId获取它的uri
                int mediaId = cursor.getInt(columnId);
                Uri itemUri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + mediaId );
                try {
                    //通过uri获取到inputStream
                    ContentResolver cr = context.getContentResolver();
                    InputStream ins=cr.openInputStream(itemUri);
                    insList.add(ins);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            } while (cursor.moveToNext());
        }
        return insList;

    }

    //name是文件名称，是MediaStore查找文件的条件之一
    public static List<InputStream> getImageIns(Context context, String name, String targetPath)  {

        List<InputStream> insList = new ArrayList<>();

        ContentResolver resolver = context.getContentResolver();

        String sortOrder = MediaStore.Images.Media.DATE_MODIFIED + " DESC";//根据日期降序查询

        String selection = MediaStore.Images.Media.BUCKET_DISPLAY_NAME + "='" + name + "'";   //查询条件 “显示名称为？”

        @SuppressLint("Recycle") Cursor cursor = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
             cursor =  resolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, selection, null, sortOrder);
        }


        OutputStream ost = null;
        try {
            ost=new FileOutputStream(new File(targetPath));


            if (cursor != null && cursor.moveToFirst()) {
                //媒体数据库中查询到的文件id
                int columnId = cursor.getColumnIndex(MediaStore.Images.Media._ID);

                do {
                    //通过mediaId获取它的uri
                    int mediaId = cursor.getInt(columnId);
                    Uri itemUri = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                         itemUri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + mediaId );
                    }
                    try {
                        //通过uri获取到inputStream
                        ContentResolver cr = context.getContentResolver();
                        InputStream ins=cr.openInputStream(itemUri);
                        insList.add(ins);

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

        return insList;

    }
}
