package br.android.bolsocasalapp.util;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfiguracaoFirebase {
    private static FirebaseAuth _firebaseAuth;
    private static DatabaseReference _databaseReference;

    public static FirebaseAuth getFirebaseAuth() {
        if (_firebaseAuth == null)
            _firebaseAuth = FirebaseAuth.getInstance();

        return _firebaseAuth;
    }

    public static DatabaseReference getDatabaseReference() {
        if (_databaseReference == null)
            _databaseReference = FirebaseDatabase.getInstance().getReference();

        return _databaseReference;
    }
}
