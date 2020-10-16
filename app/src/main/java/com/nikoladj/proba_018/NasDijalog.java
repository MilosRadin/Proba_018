package com.nikoladj.proba_018;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class NasDijalog extends AlertDialog.Builder {

    public NasDijalog(Context context){
        super(context);
        setTitle("Ulaz");
        setMessage("Dobrodosli");
        setPositiveButton("U Redu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

    }

    public AlertDialog prepareDialog(){
        AlertDialog dialog = create();
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }
}
