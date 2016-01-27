package com.layernet.utils;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;

import java.util.List;

/**
 * Created by layer on 27/1/2559.
 */
public class MediaProviderUtil {
    public static boolean isMediaProviderSupported(Activity activity)
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            final PackageManager pm = activity.getPackageManager();
            // Pick up provider with action string
            final Intent i = new Intent(DocumentsContract.PROVIDER_INTERFACE);
            final List<ResolveInfo> providers = pm.queryIntentContentProviders(i, 0);
            for (ResolveInfo info : providers)
            {
                if(info != null && info.providerInfo != null)
                {
                    final String authority = info.providerInfo.authority;
                    if(isMediaDocumentProvider(Uri.parse("content://" + authority)))
                        return true;
                }
            }
        }
        return false;
    }

    private static boolean isMediaDocumentProvider(final Uri uri)
    {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}
