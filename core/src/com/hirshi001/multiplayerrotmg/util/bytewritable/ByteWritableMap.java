package com.hirshi001.multiplayerrotmg.util.bytewritable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ByteWritableMap implements Map<String, ByteWritable>{

    private HashMap<String, ByteWritable> map = new HashMap<>();

    public void putInt(String tag, int i){
        this.put(tag, IntegerByteWritable.valueOf(i));
    }

    public void putByte(String tag, byte b){
        this.put(tag, ByteByteWritable.valueOf(b));
    }

    public void putBoolean(String tag, boolean bool){
        this.put(tag, ByteByteWritable.valueOf(bool));
    }

    public void putLong(String tag, long l){
        this.put(tag, LongByteWritable.valueOf(l));
    }

    public void putFloat(String tag, float f){
        this.put(tag, FloatByteWritable.valueOf(f));
    }

    public void putDouble(String tag, double d){
        this.put(tag, DoubleByteWritable.valueOf(d));
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public ByteWritable get(Object key) {
        return map.get(key);
    }

    @Override
    public ByteWritable put(String key, ByteWritable value) {
        return map.put(key, value);
    }

    @Override
    public ByteWritable remove(Object key) {
        return map.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends ByteWritable> m) {
        for(Map.Entry<? extends String, ? extends ByteWritable> entry:m.entrySet()){
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<String> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<ByteWritable> values() {
        return map.values();
    }

    @Override
    public Set<Entry<String, ByteWritable>> entrySet() {
        return map.entrySet();
    }
}
