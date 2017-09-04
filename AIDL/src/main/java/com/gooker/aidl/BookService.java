package com.gooker.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.gooker.aidl.domain.Book;

import java.util.ArrayList;
import java.util.List;

public class BookService extends Service {

    private static final String TAG = BookService.class.getSimpleName();

    public BookService() {

    }


    private IBinder mIBinder = new IBookServiceInterface.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public List<Book> getBooks() throws RemoteException {
            List<Book> books = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                books.add(new Book(i + "", "book Name"));
            }
            return books;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            Log.e(TAG, book.toString());
        }

        @Override
        public Book getBook() throws RemoteException {
            return new Book("id-12345", "Book Test");
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
//        return null;
    }
}
