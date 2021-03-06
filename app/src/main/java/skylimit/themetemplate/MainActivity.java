package skylimit.themetemplate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by bird on 7/15/14.
 * Updated by n-i-x on 12/5/14.
 */
public class MainActivity extends ActionBarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_layout);

        Bitmap previewImage = BitmapFactory.decodeResource(getResources(), R.drawable.preview);
        Palette p = Palette.generate(previewImage);
        final int dynamicColor = p.getDarkVibrantColor(R.color.sky);

        ImageView faceLaunchIcon = (ImageView)findViewById(R.id.faceLaunchIcon);
        ImageView hideLauncherIcon = (ImageView)findViewById(R.id.hideLauncherIcon);
        ImageView joinCommunityIcon = (ImageView)findViewById(R.id.joinCommunityIcon);
        ImageView rateApplicationIcon = (ImageView)findViewById(R.id.rateApplicationIcon);

        RelativeLayout faceLaunchItem = (RelativeLayout)findViewById(R.id.faceLaunchItem);
        TextView faceLaunchText = (TextView)findViewById(R.id.faceLaunchText);

        final PackageManager pm = getPackageManager();

        try {
            pm.getPackageInfo("boxcrab.face", PackageManager.GET_ACTIVITIES);
            faceLaunchItem.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent("boxcrab.face.LaunchTheme");
                    intent.putExtra("itemPackage", getPackageName());
                    intent.putExtra("isLocal", true);
                    startActivity(intent);
                }
            });

        } catch (PackageManager.NameNotFoundException e) {
            faceLaunchIcon.setImageResource(R.drawable.ic_gplay);
            faceLaunchText.setText(R.string.face_not_installed);
            faceLaunchItem.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    final String appPackageName = "boxcrab.face";
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
                    }
                }
            });
        }

        RelativeLayout rateApplicationItem = (RelativeLayout)findViewById(R.id.rateApplicationItem);
        rateApplicationItem.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
            }
        });

        RelativeLayout joinCommunityItem = (RelativeLayout)findViewById(R.id.joinCommunityItem);
        joinCommunityItem.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/communities/114101223634147129978")));
            }
        });

        RelativeLayout hideLauncherItem = (RelativeLayout)findViewById(R.id.hideLauncherItem);
        final ComponentName componentName = new ComponentName(this, skylimit.themetemplate.MainActivity.class);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                pm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                finish();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        builder.setMessage(R.string.hide_launcher_dialog_message)
                .setTitle(R.string.hide_launcher_dialog_title);
        final AlertDialog dialog = builder.create();

        hideLauncherItem.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.show();
            }
        });

        findViewById(R.id.themeDetails).setBackgroundColor(dynamicColor);
        faceLaunchIcon.setColorFilter(dynamicColor);
        hideLauncherIcon.setColorFilter(dynamicColor);
        joinCommunityIcon.setColorFilter(dynamicColor);
        rateApplicationIcon.setColorFilter(dynamicColor);

    }

}
