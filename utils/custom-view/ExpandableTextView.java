package com.clicknect.android.singha.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.clicknect.android.singha.R;

public class ExpandableTextView extends TextView {

    private String ellipsis = " ..";
    private CharSequence originalText;
    private CharSequence trimmedText;
    private BufferType bufferType;
    private boolean collapse = false;
    private int trimLength = 200;
    private String text;

    public ExpandableTextView(Context context) {
        this(context, null);
    }

    public ExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextView);
        this.trimLength = typedArray.getInt(R.styleable.ExpandableTextView_trimLength, trimLength);
        typedArray.recycle();

//        setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                trim = !trim;
//                setText();
//                requestFocusFromTouch();
//            }
//        });
    }

    private void setText() {
        super.setText(getDisplayableText(), bufferType);

    }

    public void setCollapseLength(int length) {
        setTrimLength(length);
        collapse();
    }

    public void setCollapseLine(final int line){
        collapse = true;
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < 16) {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                if (getLineCount() > line) {
                    int charIndex = getLayout().getLineEnd(line-1) - 2;
                    setTrimLength(charIndex);

                    collapse();

                }else {
                    trimmedText = originalText;
                }
            }
        });
    }

    public void setCollapseLine(final int line, final LineCountListener listener){

        collapse = true;
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < 16) {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                if (getLineCount() > line) {
                    int charIndex = getLayout().getLineEnd(line-1) - 2;
                    setTrimLength(charIndex);

                    collapse();
                    listener.onCount(true);
                }else {
                    trimmedText = originalText;
                    listener.onCount(false);
                }
            }
        });
    }


    private CharSequence getDisplayableText() {
        return collapse ? trimmedText : originalText;
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
    	this.text = text.toString();
        originalText = text;
        trimmedText = getTrimmedText(text);
        bufferType = type;
        setText();
    }

    private CharSequence getTrimmedText(CharSequence text) {
        if (originalText != null && originalText.length() > trimLength) {
            if (ellipsis==null)
                ellipsis = " ...";
            return new SpannableStringBuilder(originalText, 0, trimLength + 1).append(ellipsis);
        } else {
            return originalText;
        }
    }

    public CharSequence getOriginalText() {
        return originalText;
    }

    public void setTrimLength(int trimLength) {
        this.trimLength = trimLength;
        trimmedText = getTrimmedText(originalText);
        setText();
    }

    public int getTrimLength() {
        return trimLength;
    }

    public boolean isCollapse() {
        return collapse;
    }

    public void collapse() {
        this.collapse = true;
        setText();
        //requestFocusFromTouch();
    }

    public void expand(){
        this.collapse = false;
        setText();
        //requestFocusFromTouch();
    }

    public void clickExpand() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                collapse = !collapse;
                setText();
                requestFocusFromTouch();
            }
        });
    }

    public void setLineCountListener(LineCountListener lineCountListener) {
        this.lineCountListener = lineCountListener;
    }

    LineCountListener lineCountListener;

    public interface LineCountListener{
        public void onCount(boolean trim);
    }

//    @Override
//    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
//        boolean containThai = false;
//        for (String s : thaiCharacter) {
//            if (text.contains(s)) {
//                containThai = true;
//                break;
//            }
//        }
//        if (containThai) {
//            int paddingLeft = getPaddingLeft();
//            int paddingRight = getPaddingRight();
//            Log.d("fixT",""+w);
//
//            w = w - (paddingLeft + paddingRight);
//            Log.d("fixT",""+w);
//            fix_text(text, w);
//        }
//
//        super.onSizeChanged(w, h, oldW, oldH);
//
//    }
//
//    private void fix_text(String input, int width) {
//        if (this.getPaint().measureText(input) > width) {
//            Locale thaiLocale = new Locale("th");
//            BreakIterator boundary = BreakIterator.getWordInstance(thaiLocale);
//            boundary.setText(input);
//
//            String s = addDeliminator(boundary, input);
//            String deliminator = "!split!";
//            String[] tokens = (s.split(deliminator));
//
//            int length = tokens.length;
//
//            StringBuilder allString = new StringBuilder();
//            StringBuilder testString = new StringBuilder();
//            for (int i = 0; i < length; i++) {
//
//                testString.append(tokens[i]);
//                float f = this.getPaint().measureText(testString.toString());
//
//                if (width > f) {
//                    allString.append(tokens[i]);
//
//                } else if (width == f) {
//                    testString = new StringBuilder();
//                    allString.append(tokens[i]);
//                    if(!allString.toString().endsWith(" ")&&!allString.toString().endsWith("\n")){
//                        allString.append("\n");
//                    }
//                } else {
//                    testString = new StringBuilder();
//                    testString.append(tokens[i]);
//                    if(!allString.toString().endsWith(" ")&&!allString.toString().endsWith("\n")&&!tokens[i].startsWith(" ")&&!tokens[i].startsWith("\n")){
//                        allString.append("\n");
//                    }
//                    allString.append(tokens[i]);
//
//                }
//
//
//            }
//
//            setText(allString.toString());
//
//        }
//
//    }
//
//    public String addDeliminator(BreakIterator boundary, String source) {
//
//        StringBuffer stringOut = new StringBuffer();
//        int start = boundary.first();
//        for (int end = boundary.next(); end != BreakIterator.DONE; start = end, end = boundary.next()) {
//			/* use !split! as splitter cause if use - if text has - will make error */
//            stringOut.append(source.substring(start, end) + "!split!");
//        }
//        return stringOut.toString();
//    }
//
//    private String thaiCharacter[] = {
//            "ก",
//            "ข",
//            "ฃ",
//            "ค",
//            "ฅ",
//            "ฆ",
//            "ง",
//            "จ",
//            "ฉ",
//            "ช",
//            "ซ",
//            "ฌ",
//            "ญ",
//            "ฎ",
//            "ฏ",
//            "ฐ",
//            "ฑ",
//            "ฒ",
//            "ณ",
//            "ด",
//            "ต",
//            "ถ",
//            "ท",
//            "ธ",
//            "น",
//            "บ",
//            "ป",
//            "ผ",
//            "ฝ",
//            "พ",
//            "ฟ",
//            "ภ",
//            "ม",
//            "ย",
//            "ร",
//            "ล",
//            "ว",
//            "ศ",
//            "ษ",
//            "ส",
//            "ห",
//            "ฬ",
//            "อ",
//            "ฮ" };


}