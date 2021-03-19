package com.one.education.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.one.education.beans.GetFaqResponse;
import com.one.education.beans.QuestionResponse;
import com.one.education.education.R;
import com.one.education.widget.HeaderAndFooterRecyclerViewAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @创建者 Administrator
 * @创建时间 2020/4/25 22:32
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 **/

public class QuestionAdapter extends HeaderAndFooterRecyclerViewAdapter {

    private LayoutInflater layoutInflater;
    private Context mCtx;
    private List<QuestionResponse.Question> questionList;
    private Map<Integer, GetFaqResponse.GetFaq> getFaqMaps = new HashMap<>();

    private SearchListener listener;

    private GetFaqResponse.GetFaq getFaqResponse;
    private boolean mFlag;

    private Map<Integer, Boolean> maps = new HashMap<>();

    public interface SearchListener {
        void click(int position);
    }


    public boolean getFlag() {
        return mFlag;
    }

    public void getFag(int position, boolean flag, GetFaqResponse.GetFaq getFaqResponse) {
        this.getFaqResponse = getFaqResponse;
        this.mFlag = flag;
        if (maps != null && maps.size() > 0 && maps.size() > position) {
            maps.put(position, mFlag);
        }
        if (getFaqMaps != null) {
            getFaqMaps.put(position, getFaqResponse);
        }
        notifyDataSetChanged();
    }


    public void setQuestionListener(SearchListener listener) {
        this.listener = listener;
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void updateQuestionInfo(List<QuestionResponse.Question> questionList) {
        this.questionList = questionList;
        notifyDataSetChanged();
    }

    public Map<Integer, Boolean> getMap() {
        return maps;
    }

    public void setMap(Map<Integer, Boolean> maps) {
        this.maps = maps;
    }

    public QuestionAdapter(Context context, List<QuestionResponse.Question> list) {
        mCtx = context;
        questionList = list;
        layoutInflater = (LayoutInflater) mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getItemCount() {
        return questionList == null ? 0 : questionList.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        HolderView holderView = (HolderView) holder;
        holderView.question.setText(mCtx.getString(R.string.question) + " " + (position + 1) + ":");
        holderView.tv_quesition.setText(TextUtils.isEmpty(questionList.get(position).getTitle()) ? "" : questionList.get(position).getTitle());
//        if (TextUtils.isEmpty(questionList.get(position).getSummary())) {
//            holderView.contain_layout.setVisibility(View.GONE);
//        } else {
//            holderView.contain_layout.setVisibility(View.VISIBLE);
////            holderView.summy.setText(questionList.get(position).getSummary());
//        }

        //        if (mFlag) {
        //            holderView.contain_layout.setVisibility(View.VISIBLE);
        //            //            mFlag = false;
        //        } else {
        //            holderView.contain_layout.setVisibility(View.GONE);
        //            //            mFlag = true;
        //        }
        if (maps.get(position)) {
            holderView.contain_layout.setVisibility(View.VISIBLE);
            //            //            mFlag = false;
            //        } else {
            //
        } else {
            holderView.contain_layout.setVisibility(View.GONE);
        }

        //        if (getFaqResponse != null) {
        //            holderView.summy.setText(Html.escapeHtml(getFaqResponse.getContent()));
        //        }
        if (getFaqMaps != null && getFaqMaps.size() > 0 && getFaqMaps.get(position) != null) {
            holderView.summy.setText((Html.fromHtml(getFaqMaps.get(position).getContent())));

//            SpannableString spanString = new SpannableString(getFaqMaps.get(position).getContent());
//            String html = Html.toHtml(spanString);
//            String html_string = parseUnicodeToStr(html);
//            holderView.summy.setText(html_string);

        }

        holderView.question_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.click(position);
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int arg1) {

        View inflate = layoutInflater.inflate(R.layout.question_item, parent, false);
        HolderView holder = new HolderView(inflate);


        return holder;
    }

    public class HolderView extends RecyclerView.ViewHolder {

        public HolderView(View itemView) {
            super(itemView);
            tv_quesition = itemView.findViewById(R.id.tv_quesition);
            separation_line = itemView.findViewById(R.id.separation_line);
            contain_layout = itemView.findViewById(R.id.contain_layout);
            summy = itemView.findViewById(R.id.summy);
            iv_icon = itemView.findViewById(R.id.iv_icon);
            question = itemView.findViewById(R.id.question);
            question_layout = itemView.findViewById(R.id.question_layout);
        }

        TextView tv_quesition;
        View separation_line;
        LinearLayout contain_layout;
        TextView summy;
        ImageView iv_icon;
        TextView question;
        LinearLayout question_layout;

    }

    public String parseUnicodeToStr(String unicodeStr) {
        String regExp = "&#\\d*;";
        Matcher m = Pattern.compile(regExp).matcher(unicodeStr);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String s = m.group(0);
            s = s.replaceAll("(&#)|;", "");
            char c = (char) Integer.parseInt(s);
            m.appendReplacement(sb, Character.toString(c));
        }
        m.appendTail(sb);
        return sb.toString();
    }

}



