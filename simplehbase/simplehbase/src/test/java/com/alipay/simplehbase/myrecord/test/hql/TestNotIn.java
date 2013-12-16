package com.alipay.simplehbase.myrecord.test.hql;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import com.alipay.simplehbase.myrecord.MyRecord;
import com.alipay.simplehbase.myrecord.MyRecordRowKey;
import com.alipay.simplehbase.myrecord.test.TestMyRecord;
/**
 * @author xinzhi
 */
public class TestNotIn extends TestMyRecord {

    @Test
    public void testConstants() {
        put("id=0,name=aaa");
        put("id=1,name=bbb");
        put("id=2,name=bbb");

        String hql = "select where name notin ( \"aaa\" ) ";

        List<MyRecord> myRecordList = simpleHbaseClient.findObjectListByRawHql(
                new MyRecordRowKey(0), new MyRecordRowKey(100), MyRecord.class,
                hql, null);

        Assert.assertTrue(myRecordList.size() == 2);

        hql = "select where name notin ( \"bbb\" ) ";
        myRecordList = simpleHbaseClient.findObjectListByRawHql(
                new MyRecordRowKey(0), new MyRecordRowKey(100), MyRecord.class,
                hql, null);
        Assert.assertTrue(myRecordList.size() == 1);

        hql = "select where name notin ( \"aaa\" , \"bbb\" ) ";
        myRecordList = simpleHbaseClient.findObjectListByRawHql(
                new MyRecordRowKey(0), new MyRecordRowKey(100), MyRecord.class,
                hql, null);
        Assert.assertTrue(myRecordList.size() == 0);

        hql = "select where name notin ( \"ccc\" ) ";
        myRecordList = simpleHbaseClient.findObjectListByRawHql(
                new MyRecordRowKey(0), new MyRecordRowKey(100), MyRecord.class,
                hql, null);
        Assert.assertTrue(myRecordList.size() == 3);
    }

    @Test
    public void testVar() {
        put("id=0,name=aaa");
        put("id=1,name=bbb");
        put("id=2,name=bbb");

        String hql = "select where name notin #nameList#";
        Map<String, Object> para = new HashMap<String, Object>();

        para.put("nameList", Arrays.asList("aaa"));

        List<MyRecord> myRecordList = simpleHbaseClient.findObjectListByRawHql(
                new MyRecordRowKey(0), new MyRecordRowKey(100), MyRecord.class,
                hql, para);
        Assert.assertTrue(myRecordList.size() == 2);

        para.put("nameList", Arrays.asList("bbb"));
        myRecordList = simpleHbaseClient.findObjectListByRawHql(
                new MyRecordRowKey(0), new MyRecordRowKey(100), MyRecord.class,
                hql, para);
        Assert.assertTrue(myRecordList.size() == 1);

        para.put("nameList", Arrays.asList("aaa", "bbb"));
        myRecordList = simpleHbaseClient.findObjectListByRawHql(
                new MyRecordRowKey(0), new MyRecordRowKey(100), MyRecord.class,
                hql, para);
        Assert.assertTrue(myRecordList.size() == 0);

        para.put("nameList", Arrays.asList("ccc"));
        myRecordList = simpleHbaseClient.findObjectListByRawHql(
                new MyRecordRowKey(0), new MyRecordRowKey(100), MyRecord.class,
                hql, para);
        Assert.assertTrue(myRecordList.size() == 3);
    }

}