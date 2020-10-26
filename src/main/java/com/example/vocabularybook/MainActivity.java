package com.example.vocabularybook;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TableLayout;

import com.example.vocabularybook.wordcontract.Words;

public class MainActivity extends AppCompatActivity implements
        WordItemFragment.OnFragmentInteractionListener,
        WordDetailFragment.OnFragmentInteractionListener {//当用户在单词详细Fragment中单击时回调此函数

    @Override
    public void onWordDetailClick(Uri uri) {
    }   //当用户在单词列表Fragment中单击某个单词时回调此函数,判断如果横屏的话，则需要在右侧单词详细Fragment中显示

    @Override

    public void onWordItemClick(String id) {
        if (isLand()) {//横屏的话则在右侧的WordDetailFragment中显示单词详细信息
            ChangeWordDetailFragment(id);
        } else {
            Intent intent = new Intent(MainActivity.this, WordDetailActivity.class);
            intent.putExtra(WordDetailFragment.ARG_ID, id);
            startActivity(intent);
        }
    }

    @Override

    public void onDeleteDialog(String strId) {
        DeleteDialog(strId);
    }

    @Override

    public void onUpdateDialog(String strId) {
        WordsDB wordsDB = WordsDB.getWordsDB();
        if (wordsDB != null && strId != null) {
            Words.WordDescription item = wordsDB.getSingleWord(strId);
            if (item != null) {
                UpdateDialog(strId, item.word, item.meaning, item.sample);
            }
        }
    }

    //是否是横屏
    private boolean isLand() {    /*    Fragment fragment=getFragmentManager().findFragmentById(R.id.worddetail);    if(fragment==null)        return false;    return true;    */
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            return true;
        return false;
    }

    private void ChangeWordDetailFragment(String id) {
        Bundle arguments = new Bundle();
        arguments.putString(WordDetailFragment.ARG_ID, id);
        Log.v(TAG, id);
        WordDetailFragment fragment = new WordDetailFragment();
        fragment.setArguments(arguments);
        getFragmentManager().beginTransaction().replace(R.id.worddetail, fragment).commit();
    }

    //新增对话框
    private void InsertDialog() {
        final TableLayout tableLayout = (TableLayout) getLayoutInflater().inflate(R.layout.insert, null);
        new AlertDialog.Builder(this).

                setTitle("新增单词").//标题

                setView(tableLayout)//设置视图
                // 确定按钮及其动作
                .

                        setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override

                            public void onClick
                                    (DialogInterface dialogInterface, int i) {
                                String strWord = ((EditText) tableLayout.findViewById(R.id.txtWord)).getText().toString();
                                String strMeaning = ((EditText) tableLayout.findViewById(R.id.txtMeaning)).getText().toString();
                                String strSample = ((EditText) tableLayout.findViewById(R.id.txtSample)).getText().toString();                   //既可以使用Sql语句插入，也可以使用使用insert方法插入
                                // InsertUserSql(strWord, strMeaning, strSample);
                                WordsDB wordsDB = WordsDB.getWordsDB();
                                wordsDB.Insert(strWord, strMeaning, strSample);                    //单词已经插入到数据库，更新显示列表
                                RefreshWordItemFragment();
                            }
                        })                   //取消按钮及其动作
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override

                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).create()//创建对话框
                .show();//显示对话框
    }

    //删除对话框
    private void DeleteDialog(final String strId) {
        new AlertDialog.Builder(this).setTitle("删除单词")
                .setMessage("是否真的删除单词?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override

                    public void onClick(DialogInterface dialogInterface, int i) {         //既可以使用Sql语句删除，也可以使用使用delete方法删除
                        WordsDB wordsDB = WordsDB.getWordsDB();
                        wordsDB.DeleteUseSql(strId);            //单词已经删除，更新显示列表
                        RefreshWordItemFragment();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override

            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).create().show();
    }

    //修改对话框
    private void UpdateDialog(final String strId, final String strWord, final String strMeaning, final String strSample) {
        final TableLayout tableLayout = (TableLayout) getLayoutInflater().inflate(R.layout.insert, null);
        ((EditText) tableLayout.findViewById(R.id.txtWord)).setText(strWord);
        ((EditText) tableLayout.findViewById(R.id.txtMeaning)).setText(strMeaning);
        ((EditText) tableLayout.findViewById(R.id.txtSample)).setText(strSample);
        new AlertDialog.Builder(this)
                .setTitle("修改单词")//标题
                .setView(tableLayout)//设置视图
                // 确定按钮及其动作
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick
                            (DialogInterface dialogInterface, int i) {
                        String strNewWord = ((EditText) tableLayout.findViewById(R.id.txtWord)).getText().toString();
                        String strNewMeaning = ((EditText) tableLayout.findViewById(R.id.txtMeaning)).getText().toString();
                        String strNewSample = ((EditText) tableLayout.findViewById(R.id.txtSample)).getText().toString();                   //既可以使用Sql语句更新，也可以使用使用update方法更新
                        WordsDB wordsDB = WordsDB.getWordsDB();
                        wordsDB.UpdateUseSql(strId, strWord, strNewMeaning, strNewSample);                    //单词已经更新，更新显示列表
                        RefreshWordItemFragment();
                    }
                })                   //取消按钮及其动作
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override

                    public void onClick
                            (DialogInterface dialogInterface, int i) {
                    }
                }).create()//创建对话框
                .show();//显示对话框
    }

    //查找对话框
    private void SearchDialog() {
        final TableLayout tableLayout = (TableLayout) getLayoutInflater()
                .inflate(R.layout.searchterm, null);
        new AlertDialog.Builder(this)
                .setTitle("查找单词")//标题            .setView(tableLayout)//设置视图
                // 确定按钮及其动作
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String txtSearchWord = ((EditText) tableLayout.findViewById(R.id.txtSearchWord))
                                .getText().toString();                    //单词已经插入到数据库，更新显示列表
                        RefreshWordItemFragment(txtSearchWord);
                    }
                })        //取消按钮及其动作
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override

                    public void onClick
                            (DialogInterface dialogInterface, int i) {
                    }
                }).create()//创建对话框
                .show();//显示对话框
    }

    /**
     * 更新单词列表
     */

    private void RefreshWordItemFragment() {
        WordItemFragment wordItemFragment = (WordItemFragment) getFragmentManager()
                .findFragmentById(R.id.wordslist);
        wordItemFragment.refreshWordsList();
    }

    /**
     * 更新单词列表
     */

    private void RefreshWordItemFragment(String strWord) {
        WordItemFragment wordItemFragment = (WordItemFragment) getFragmentManager()
                .findFragmentById(R.id.wordslist);
        wordItemFragment.refreshWordsList(strWord);
    }

}
