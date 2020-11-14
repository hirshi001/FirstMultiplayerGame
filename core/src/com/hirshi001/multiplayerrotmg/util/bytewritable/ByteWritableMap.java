package com.hirshi001.multiplayerrotmg.util.bytewritable;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class ByteWritableMap implements Map<String, ByteWritable>{

    private HashMap<String, ByteWritable> map = new HashMap<>();
    public final Queue<String> updated = new LinkedList<>();

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
        return this.map.size();
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.map.containsValue(value);
    }

    @Override
    public ByteWritable get(Object key) {
        return this.map.get(key);
    }

    @Override
    public ByteWritable put(String key, ByteWritable value) {
        this.updated.add(key);
        return this.map.put(key, value);
    }

    @Override
    public ByteWritable remove(Object key) {
        return this.map.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends ByteWritable> m) {
        for(Map.Entry<? extends String, ? extends ByteWritable> entry:m.entrySet()){
            this.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        this.map.clear();
    }

    @Override
    public Set<String> keySet() {
        return this.map.keySet();
    }

    @Override
    public Collection<ByteWritable> values() {
        return this.map.values();
    }

    @Override
    public Set<Entry<String, ByteWritable>> entrySet() {
        return this.map.entrySet();
    }
}
