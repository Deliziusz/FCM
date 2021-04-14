package zombie.deliziusz.fcm;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class Fcm extends FirebaseMessagingService {
    //Para usar el token en el dispositivo
    //con este token podemos envíar una notificación específica
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        //MOSTRAMOS EL VALOR DEL TOKEN
        Log.e("token","mi token es:"+s);
        //metodo para guardar token
        guardartoken(s);
    }

    private void guardartoken(String s) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("token");
        ref.child("deliziusz").setValue(s);
    }

    //Recibe las notificaciones y datos
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        //RECIBIENDO INFORMACION DE LOS DATOS
        String from = remoteMessage.getFrom();
        

        //PARA RECIBIR UNA NOTIFICACION CLAVE VALOR
        if(remoteMessage.getData().size() > 0 ){
            Log.e("tag", "Titulo es:"+remoteMessage.getData().get("titulo"));
            Log.e("tag", "Detalle es:"+remoteMessage.getData().get("detalle"));
            Log.e("tag", "El color es: "+remoteMessage.getData().get("color"));
            

        }
    }

    private void mayorqueoreo() {
        String id= "mensaje";
        NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,id);
        //Para evitar que la app colapse si se utiliza una version menor a oreo
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel nc = new NotificationChannel(id,"nuevo",NotificationManager.IMPORTANCE_HIGH);
            nc.setShowBadge(true);
            nm.createNotificationChannel(nc);
        }
        //Para que cuando toquen la notificación se pueda cancelar
        builder.setAutoCancel()
    }
}
