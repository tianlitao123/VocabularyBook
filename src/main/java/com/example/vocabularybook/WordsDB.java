package com.example.vocabularybook;

import android.database.Cursor;

import com.example.vocabularybook.wordcontract.Words;

import java.util.ArrayList;
import java.util.Map;

public class WordsDB {
    private static final String TAG = "myTag";
    private static WordsDBHelper mDbHelper;
    //采用单例模式
    private static WordsDB instance = new WordsDB();

    public static WordsDB getWordsDB() {
        return WordsDB.instance;
    }

    private WordsDB() {
        if (mDbHelper == null) {
            mDbHelper = new WordsDBHelper(WordsApplication.getContext());
        }
    }

    public void close() {

        if (mDbHelper != null) mDbHelper.close();
    }

    //获得单个单词的全部信息
    public Words.WordDescription getSingleWord(String id) {
    }

    //得到全部单词列表
    public ArrayList<Map<String, String>> getAllWords() {
    }

    //将游标转化为单词列表
    private ArrayList<Map<String, String>> ConvertCursor2WordList(Cursor cursor) {
    }

    //增加单词
    public void InsertUserSql(String strWord, String strMeaning, String strSample) {
    }

    public void Insert(String strWord, String strMeaning, String strSample) {
    }   //删除单词

    public void DeleteUseSql(String strId) {
    }

    public void Delete(String strId) {
    }    //更新单词

    public void UpdateUseSql(String strId, String strWord, String strMeaning, String strSample) {
    }

    public void Update(String strId, String strWord, String strMeaning, String strSample) {
    }

    //查找
    public ArrayList<Map<String, String>> SearchUseSql(String strWordSearch) {
    }

    public ArrayList<Map<String, String>> Search(String strWordSearch) {
    }

}
