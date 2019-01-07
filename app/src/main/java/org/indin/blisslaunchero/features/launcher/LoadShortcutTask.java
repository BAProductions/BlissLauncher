package org.indin.blisslaunchero.features.launcher;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Process;

import org.indin.blisslaunchero.features.shortcuts.DeepShortcutManager;
import org.indin.blisslaunchero.features.shortcuts.ShortcutInfoCompat;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadShortcutTask extends AsyncTask<Void, Void, Map<String,ShortcutInfoCompat>> {

    private final WeakReference<Context> mContext;
    private WeakReference<AppProvider> mAppProvider;

    private static final String TAG = "LoadShortcutTask";

    LoadShortcutTask(Context context) {
        super();
        this.mContext = new WeakReference<>(context);
    }

    public void setAppProvider(AppProvider appProvider) {
        this.mAppProvider = new WeakReference<>(appProvider);
    }

    @Override
    protected Map<String,ShortcutInfoCompat> doInBackground(Void... voids) {
        List<ShortcutInfoCompat> list = DeepShortcutManager.getInstance(mContext.get()).queryForPinnedShortcuts(null,
                Process.myUserHandle());
        Map<String, ShortcutInfoCompat> shortcutInfoMap = new HashMap<>();
        for (ShortcutInfoCompat shortcutInfoCompat : list) {
            shortcutInfoMap.put(shortcutInfoCompat.getId(), shortcutInfoCompat);
        }
        return shortcutInfoMap;
    }

    @Override
    protected void onPostExecute(Map<String, ShortcutInfoCompat> shortcuts) {
        super.onPostExecute(shortcuts);
        if(mAppProvider != null){
            mAppProvider.get().loadShortcutsOver(shortcuts);
        }
    }
}
