package com.example.demo.utils.framework.serialization;

import com.example.demo.utils.framework.exection.Exceptions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class BytesUtil {

    @SuppressWarnings("unchecked")
	public static <T extends Serializable> T toObject(byte[] value, Class<T> clazz) throws Exception {
    	 T t = null;
         final ByteArrayInputStream inputStream = new ByteArrayInputStream(value);
         try {
             ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
			t = (T) objectInputStream.readObject();
         } catch (Exception e) {
             Exceptions.handle("Deserializer Bytes Error.", e);
         }
         return t;
    }
    
    public static <T extends Serializable> byte[] toBytes(T t) throws Exception
    {
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream streamOut = new ObjectOutputStream(baos);
            streamOut.writeObject(t);
        } catch (IOException e) {
            Exceptions.handle("Serializer Bytes Error.", e);
        }
        return baos.toByteArray();
    }
    
    public static byte[] toBytes(Object object) throws Exception
    {
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream streamOut = new ObjectOutputStream(baos);
            streamOut.writeObject(object);
        } catch (IOException e) {
            Exceptions.handle("Serializer Bytes Error.", e);
        }
        return baos.toByteArray();
    }
}