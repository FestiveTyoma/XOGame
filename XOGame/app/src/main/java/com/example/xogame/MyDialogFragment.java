package com.example.xogame;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

public class MyDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialogForExamples)
                .setPositiveButton(R.string.posButOk, (dialog, id) -> {
                    Toast.makeText(getContext(),"Ошибка ожидания", Toast.LENGTH_SHORT);
                });

        // Create the AlertDialog object and return it
        return builder.create();
    }


}


