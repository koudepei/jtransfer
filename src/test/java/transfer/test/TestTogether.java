package transfer.test;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import com.jake.common.util.JsonUtils;
import com.jake.common.util.ProtostuffUtils;
import transfer.ByteArray;
import transfer.Transfer;
import transfer.def.TransferConfig;

import java.io.IOException;

/**
 * Created by Administrator on 2015/2/26.
 */
public class TestTogether {

    public static void main(String[] args) {

        TransferConfig.registerClass(Entity.class, 1);

        Transfer.encodePreCompile(Entity.class);

        Entity entity = new Entity();
        entity.setId(1L);
        entity.setName("Jake");
        entity.setStr("str");
        entity.setBool(true);
        entity.setUid(101);
        entity.getFriends().add(1l);
        entity.getFriends().add(2l);
        entity.getFriends().add(3l);

        long t1 = 0l;

        Codec<Entity> simpleTypeCodec = ProtobufProxy
                .create(Entity.class);

        try {
            byte[] bb0 = simpleTypeCodec.encode(entity);
            System.out.println(bb0.length);

            t1 = System.currentTimeMillis();
            for (int i = 0; i < 10000000;i++) {

                // 序列化
                byte[] bb = simpleTypeCodec.encode(entity);
                // 反序列化
                Entity newStt = simpleTypeCodec.decode(bb);
            }
            System.out.println("protobuff : " + (System.currentTimeMillis() - t1));
        } catch (IOException e) {
            e.printStackTrace();
        }


        t1 = System.currentTimeMillis();
        ByteArray byteArray = Transfer.encode(entity, Entity.class).getByteArray();
        System.out.println(byteArray.toBytes().length);
        for (int i = 0; i < 10000000;i++) {
            byteArray = Transfer.encode(entity, Entity.class).getByteArray();
            Transfer.decode(byteArray, Entity.class);
        }
        System.out.println("the transfer : " + (System.currentTimeMillis() - t1));


        byte[] bytes0 = ProtostuffUtils.object2Bytes(entity);
        System.out.println(bytes0.length);
        t1 = System.currentTimeMillis();
        for (int i = 0; i < 10000000;i++) {
            byte[] bytes = ProtostuffUtils.object2Bytes(entity);
            ProtostuffUtils.bytes2Object(bytes, Entity.class);
        }
        System.out.println("protostuff : " + (System.currentTimeMillis() - t1));



        t1 = System.currentTimeMillis();
        for (int i = 0; i < 10000000;i++) {
            byte[] bytes = JsonUtils.object2Bytes(entity);
            JsonUtils.bytes2Object(bytes);
        }
        System.out.println("fastjson to bytes : " + (System.currentTimeMillis() - t1));



        t1 = System.currentTimeMillis();
        for (int i = 0; i < 10000000;i++) {
            String s = JsonUtils.object2JsonString(entity);
            JsonUtils.jsonString2Object(s, Entity.class);
        }
        System.out.println("fastjson to string : " + (System.currentTimeMillis() - t1));




        t1 = System.currentTimeMillis();
        for (int i = 0; i < 10000000;i++) {
            String s = JacksonUtils.object2JsonString(entity);
            JacksonUtils.jsonString2Object(s, Entity.class);
        }
        System.out.println("jackson : " + (System.currentTimeMillis() - t1));





        System.out.println(JsonUtils.object2JsonString(entity).getBytes().length);
        System.out.println(JsonUtils.object2Bytes(entity).length);




    }

}