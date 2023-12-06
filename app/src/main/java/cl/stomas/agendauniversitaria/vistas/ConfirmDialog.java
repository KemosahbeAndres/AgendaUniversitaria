package cl.stomas.agendauniversitaria.vistas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import cl.stomas.agendauniversitaria.R;

public class ConfirmDialog extends DialogFragment {
    private String message, title;
    private Context context;
    private onDialogResponseListener listener;

    private ConfirmDialog(Context context, String title, String message, onDialogResponseListener listener) {
        this.context = context;
        this.title = title;
        this.message = message;
        this.listener = listener;
    }

    public static ConfirmDialog BuildConfirmDialog(Context context, String title, String message, onDialogResponseListener listener){
        return new ConfirmDialog(context, title, message, listener);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        builder.setMessage(message)
                .setTitle(title)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogConfirm(true);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogConfirm(false);
                    }
                });
        return builder.create();
    }

}
