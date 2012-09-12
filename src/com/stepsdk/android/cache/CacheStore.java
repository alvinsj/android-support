/**
 * Copyright (c) 2012 Alvin S.J. Ng
 * 
 * Permission is hereby granted, free of charge, to any person obtaining 
 * a copy of this software and associated documentation files (the 
 * "Software"), to deal in the Software without restriction, including 
 * without limitation the rights to use, copy, modify, merge, publish, 
 * distribute, sublicense, and/or sell copies of the Software, and to 
 * permit persons to whom the Software is furnished to do so, subject 
 * to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be 
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT 
 * WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF 
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR 
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT 
 * SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE 
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, 
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR 
 * IN CONNECTION WITH THE SOFTWARE OR 
 * THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 * @author 		Alvin S.J. Ng <alvinsj.ng@gmail.com>
 * @copyright	2012	Alvin S.J. Ng
 * 
 */
package com.stepsdk.android.cache;


import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.provider.BaseColumns;

import java.util.HashMap;
import java.util.Iterator;

public class CacheStore {
    private CacheStoreOpenHelper dbHelper;
    private static CacheStore singleton;
    private static final String PAIR_DELIMITER = "<#=#>";
    private static final String KEY_VALUE_DELIMITER = ">#=#>";
    
    private CacheStore(Context context){
        dbHelper = new CacheStoreOpenHelper(context);
    }
    
    public static CacheStore getInstance(Context context){
        if(singleton == null)
            singleton = new CacheStore(context);
        return singleton;
    }
    
    public boolean put(Cachable c){
        dbHelper.put(c.cacheType(), c.cacheId(), cacheHashMap(c.toCache()) );
        return true;
    }
    
    public boolean put(String type, String key, String c){
        dbHelper.put(type, key, c);
        return true;
    }
    
    public String get(String type, String key){
        String c = dbHelper.get(type, key);
        return c;
    }
    
    public Cachable getMatched(Cachable c, String type, String key){
        String r =  dbHelper.get(type, key);

        return c.fromCache(type, key, tokenize(r));
    }
    
    public Cachable getLatest(Cachable c, String type, String key){
        String r = dbHelper.get(type, key); 
        if( r == null )
        	r = dbHelper.getLatest(type);

        return c.fromCache(type, key, tokenize(r));
    }
    
    private String cacheHashMap(HashMap<String, String> c){
        Iterator<String> i = c.keySet().iterator();
        String detoken = "";
        while(i.hasNext()){
            String key = i.next();
            detoken = detokenize( detoken, key, c.get(key) );
        }
        if(detoken.length()==0)
            detoken = null;
        return detoken;
    }
    
    private String detokenize(String extend, String key, String value ){
        if(extend.length()!=0)
            extend.concat(PAIR_DELIMITER);
        return extend.concat(key+KEY_VALUE_DELIMITER+value);
    }
    
    private HashMap<String, String> tokenize(String extend){
        HashMap<String, String> map = new HashMap<String, String>();
        if(extend == null)
            return map;
        
        String[] spl = null;
        if(extend.length()!=0)
            spl = extend.split(PAIR_DELIMITER);
        if(spl != null)
            for(int i=0; i<spl.length; i++){
                String[] kv = spl[i].split(KEY_VALUE_DELIMITER);
                map.put(kv[0], kv[1]);
            }
        return map;
    }

    public void remove(String type, String cacheId) {
        dbHelper.delete(type, cacheId);
    }

    public void clear() {
        dbHelper.drop();
    }
   
}
