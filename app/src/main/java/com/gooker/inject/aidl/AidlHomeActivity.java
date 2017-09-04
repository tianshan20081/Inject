package com.gooker.inject.aidl;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.gooker.aidl.IBookServiceInterface;
import com.gooker.aidl.domain.Book;
import com.gooker.aidl.domain.BookService;
import com.gooker.inject.R;
import com.gooker.stat.utils.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AidlHomeActivity extends AppCompatActivity {

    @BindView(R.id.tv_bind_service)
    TextView tvBindService;
    @BindView(R.id.tv_unbind_service)
    TextView tvUnbindService;
    @BindView(R.id.tv_message)
    TextView tvMessage;

    private volatile boolean isConnected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl_home);
        ButterKnife.bind(this);


    }


    private IBookServiceInterface iBookServiceInterface;


    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            isConnected = true;
            toast("onServiceConnected");
            Logger.e("onServiceConnected");
            iBookServiceInterface = IBookServiceInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isConnected = false;
            toast("onServiceDisconnected");
            Logger.e("onServiceDisconnected");
        }
    };

    private void toast(String message) {
        Toast.makeText(AidlHomeActivity.this, message, Toast.LENGTH_LONG).show();
    }


    @OnClick(R.id.tv_add_book)
    void addBook() {
        try {
            if (iBookServiceInterface == null) {
                toast("Please start bind Service");
                return;
            }
            iBookServiceInterface.addBook(new Book("1234", "人生不能承受之轻"));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    @OnClick(R.id.tv_get_book)
    void getBook() {
        try {
            if (iBookServiceInterface == null) {
                toast("Please start bind Service");
                return;
            }
            Book book = iBookServiceInterface.getBook();
            Logger.e(book.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.tv_get_books)
    void getBooks() {
        try {
            if (iBookServiceInterface == null) {
                toast("Please start bind Service");
                return;
            }
            List<Book> books = iBookServiceInterface.getBooks();
            for (Book book : books) {
                Logger.e(book.toString());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    @OnClick(R.id.tv_bind_service)
    void bindService() {

        if (isConnected) {
            toast("Have connected Success !!!");
            return;
        }
        Intent intent = new Intent(AidlHomeActivity.this, BookService.class);

        bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE);
    }

    @OnClick(R.id.tv_unbind_service)
    void unBindService() {
        if (!isConnected) {
            toast("Have disconnected Success !!!");
            return;
        }
        unbindService(serviceConnection);
        isConnected = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!isConnected) {
            toast("Have disconnected Success !!!");
            return;
        }
        unbindService(serviceConnection);
        isConnected = false;
    }
}
