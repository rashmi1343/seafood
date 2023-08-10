package com.app.seafoodapp.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.app.seafoodapp.Const.ConstApi

import com.app.seafoodapp.Interface.OnSelectSession
import com.app.seafoodapp.Network.HttpTask
import com.app.seafoodapp.R
import com.app.seafoodapp.model.ResultSeminar
import com.app.seafoodapp.model.SeminarSession
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class TrainingActivity : AppCompatActivity(), View.OnClickListener, OnSelectSession {
    private var mTrainingRecycler: RecyclerView? = null
    private val mSessionText: TextView? = null
    private var mTrainingList: MutableList<ResultSeminar> = ArrayList<ResultSeminar>()
    private val mMasterSeminarList: MutableList<SeminarSession> = ArrayList<SeminarSession>()
    private val mSearchSeminarList: MutableList<SeminarSession> = ArrayList<SeminarSession>()
    private var mClose: ImageView? = null
    private var mBack: ImageView? = null
    private val mSessionSpinner: Spinner? = null
    var mMedium: Typeface? = null
    private var mProgressBar: ProgressBar? = null

    //private SeminarSessionAdapter mAdapter;
    var llseminarmainheadingsession: LinearLayout? = null
    var llseminarsessiontitle: LinearLayout? = null
    var edtsearchsessionview: EditText? = null
    var session_spinner: Spinner? = null
    private val mTrainingScroll: ScrollView? = null
    private var mRetryLayout: RelativeLayout? = null
    private var mRetryButton: AppCompatButton? = null
    private val mNoLayout: RelativeLayout? = null
    var cdsession: CardView? = null

    //private RelativeLayout mRetryLayout;
    //private AppCompatButton mRetryButton;
    var navigationcode: String? = null
    private val mSessionList: MutableList<String> = ArrayList()
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training)
        init()
        if (getIntent().getStringExtra("navigationcode") != null) {
            navigationcode = getIntent().getStringExtra("navigationcode")
        }
        if (com.app.seafoodapp.ui.util.isOnline(this@TrainingActivity)) {
            getSeminar(this)
        } else {
            mProgressBar!!.setVisibility(View.GONE)
            mTrainingScroll!!.setVisibility(View.GONE)
            cdsession!!.setVisibility(View.GONE)
            mNoLayout!!.setVisibility(View.VISIBLE)
        }
        mClose!!.setOnClickListener(this)
        mBack!!.setOnClickListener(this)
//        mBack!!.setOnClickListener(View.OnClickListener {
//            finish()
//            overridePendingTransition(R.anim.slide_in_left, R.anim.slideout_right)
//        })
//        mClose!!.setOnClickListener(View.OnClickListener {
//            finish()
//            overridePendingTransition(R.anim.slide_in_left, R.anim.slideout_right)
//        })
    }

    private fun init() {
        mRetryLayout = findViewById<RelativeLayout>(R.id.retry_layout)
        mRetryButton = findViewById<AppCompatButton>(R.id.retry_button)
        mTrainingRecycler = findViewById<RecyclerView>(R.id.training_recycler)
        llseminarmainheadingsession = findViewById<LinearLayout>(R.id.llseminarmainheadingsession)
        // llseminarsessiontitle = findViewById(R.id.llseminarsessiontitle);
        mClose = findViewById<ImageView>(R.id.close_training)
        mBack = findViewById<ImageView>(R.id.back_training)
        mProgressBar = findViewById<ProgressBar>(R.id.training_progress)
        edtsearchsessionview = findViewById<View>(R.id.edtsearchsessionview) as EditText?
        edtsearchsessionview!!.setVisibility(View.GONE)
        session_spinner = findViewById<Spinner>(R.id.session_spinner)
        cdsession = findViewById<CardView>(R.id.cdsession)
        mSessionList.clear()
        mSessionList.add("Select Seminar Session")
        session_spinner!!.setSelection(0)
        cdsession!!.setVisibility(View.GONE)


        /*edtsearchsessionview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("search string", charSequence.toString());
                if(charSequence.toString().length()>6) {
                    filtersession(charSequence.toString());
                }
                else {
                    if(llseminarmainheadingsession!=null) {
                        llseminarmainheadingsession.removeAllViews();
                        createSeminarlist(mMasterSeminarList);
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //after the change calling the method and passing the search input

            }
        });*/
    }

    private fun filtersession(sessioname: String) {
        //new array list that will hold the filtered data
        for (ss in mMasterSeminarList) {
            if (ss.seminarSessionName.toLowerCase()
                    .contains(sessioname.lowercase(Locale.getDefault()))
            ) {
                mSearchSeminarList.add(ss)
            }
        }
        if (llseminarmainheadingsession != null) {
            llseminarmainheadingsession!!.removeAllViews()
            createSeminarlist(mSearchSeminarList)
        }
    }

    override fun onClick(v: View) {
        if (v === mClose) {
            //  finish();
            val myIntent = Intent(this@TrainingActivity, DashBoardActivity::class.java)
            // myIntent.putExtra("navigationcode", "1");
            startActivity(myIntent)
            overridePendingTransition(R.anim.slidein_right, R.anim.slide_out_left)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slideout_right)
        }
        if (v === mBack) {
            //  finish();
            //  overridePendingTransition(R.anim.slide_in_left, R.anim.slideout_right);
            val myIntent = Intent(this@TrainingActivity, DashBoardActivity::class.java)
            // myIntent.putExtra("navigationcode", "1");
            startActivity(myIntent)
            overridePendingTransition(R.anim.slideout_right, R.anim.slide_in_left)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val myIntent = Intent(this@TrainingActivity, DashBoardActivity::class.java)
        startActivity(myIntent)
    }

    override fun clicktogetSession(query: String?) {
        mQuerylayout!!.setVisibility(View.VISIBLE)
    }

    private fun getSeminar(context: Activity) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("methodname", "getSeminar")
        Log.d("seminarstatus", jsonObject.toString())
        HttpTask {
            ""
            if (it == null) {
                Log.d("connection error", "Some thing Went Wrong")

                return@HttpTask
            }
            try {


                val seminarjsonobj = JSONObject(it.toString())
                Log.d("respond", seminarjsonobj.toString())

                val status = seminarjsonobj.getString("status");
                val message = seminarjsonobj.getString("msg");

                if (status == "200") {
                    var countermaster = 0
                    mProgressBar!!.setVisibility(View.GONE)
                    mTrainingRecycler!!.setVisibility(View.VISIBLE)


                    if (message!!.contains("OK")) {

                       // val get_result: String? = seminarjsonobj.getmResultSeminar()
                        val result = seminarjsonobj.getString("result")
                        try {
                            val jsonArray = JSONArray(result)
                            for (i in 0 until jsonArray.length()) {
                                val `object`: JSONObject = jsonArray.getJSONObject(i)
                                val gson = Gson()
                                val resultSeminar: ResultSeminar =
                                    gson.fromJson(`object`.toString(), ResultSeminar::class.java)
                                val seminar_id: String? = resultSeminar.getmSeminarId()
                                val session_id: String? = resultSeminar.getmSessionId()
                                val session_name: String? = resultSeminar.getmSessionName()
                                val session_title: String? = resultSeminar.getmSessionTitle()
                                val seminar_title: String? = resultSeminar.getmSeminarTitle()
                                Log.d(TAG, "onEmminarResponse: $seminar_title")
                                val seminar_owner: String? = resultSeminar.getmSeminarOwner()
                                val seminar_date: String?= resultSeminar.getmSeminarDate()
                                val seminar_start: String? = resultSeminar.getmSeminarStart()
                                val seminar_end: String? = resultSeminar.getmSeminarEnd()
                                // mSessionNameList.add(session_name);

                                // SeminarSession mstrsessionname = (SeminarSession) mMasterSeminarList.get(countermaster -1).
                                if (mMasterSeminarList.size == 0) {
                                   // val ssession = SeminarSession(session_name!!, mTrainingList)
                                   // ssession.seminarSessionId?.get(session_id!!.toInt())
                               //     ssession.seminarSessionTitle?.get(session_title!!.toInt())
                              //      ssession.seminarSessionName.get(session_name.toInt())

                                 //   ssession.seminarSessionTitle?.get(session_title)
                                  //  ssession.seminarSessionName.get(session_name)

                                  //  mMasterSeminarList.add(ssession)
                                    mMasterSeminarList.add(SeminarSession(session_name!!, mTrainingList))
                                    mSessionList.add("$session_name:$seminar_date")
                                    countermaster = countermaster + 1
                                    val seminar_result = ResultSeminar()
                                    seminar_result.setmSessionName(session_name)
                                    seminar_result.setmSeminarId(seminar_id)
                                    seminar_result.setmSeminarDate(seminar_date)
                                    seminar_result.setmSessionTitle(session_title)
                                    seminar_result.setmSeminarOwner(seminar_owner)
                                    seminar_result.setmSeminarStart(seminar_start)
                                    seminar_result.setmSeminarEnd(seminar_end)
                                    seminar_result.setmSessionId(session_id)
                                    seminar_result.setmSeminarTitle(seminar_title)
                                    mTrainingList.add(seminar_result)
                                } else if (mMasterSeminarList[countermaster - 1].seminarSessionName.equals(session_name,ignoreCase = true)

                                ) {
                                    val seminar_result = ResultSeminar()
                                    seminar_result.setmSessionName(session_name)
                                    seminar_result.setmSeminarId(seminar_id)
                                    seminar_result.setmSeminarDate(seminar_date)
                                    seminar_result.setmSessionTitle(session_title)
                                    seminar_result.setmSeminarOwner(seminar_owner)
                                    seminar_result.setmSeminarStart(seminar_start)
                                    seminar_result.setmSeminarEnd(seminar_end)
                                    seminar_result.setmSessionId(session_id)
                                    seminar_result.setmSeminarTitle(seminar_title)
                                    mTrainingList.add(seminar_result)
                                } else if (!mMasterSeminarList[countermaster - 1].seminarSessionName.equals(session_name,ignoreCase = true)
                                ) {
                                    mTrainingList = ArrayList<ResultSeminar>()
//                                    val ssession = SeminarSession(session_name!!, mTrainingList)
//                                    ssession.seminarSessionId!!.get(session_id!!.toInt())
//                                    ssession.seminarSessionTitle!!.get(session_title!!.toInt())
//                                    ssession.seminarSessionName!!.get(session_name!!.toInt())
                                    mMasterSeminarList.add(SeminarSession(session_name!!, mTrainingList))
                                    mSessionList.add("$session_name:$seminar_date")
                                    countermaster = countermaster + 1
                                    val seminar_result = ResultSeminar()
                                    seminar_result.setmSessionName(session_name)
                                    seminar_result.setmSeminarId(seminar_id)
                                    seminar_result.setmSeminarDate(seminar_date)
                                    seminar_result.setmSessionTitle(session_title)
                                    seminar_result.setmSeminarOwner(seminar_owner)
                                    seminar_result.setmSeminarStart(seminar_start)
                                    seminar_result.setmSeminarEnd(seminar_end)
                                    seminar_result.setmSessionId(session_id)
                                    seminar_result.setmSeminarTitle(seminar_title)
                                    mTrainingList.add(seminar_result)
                                }

                                /*


                                if(mTrainingList.size()==0)
                                {
                                    SeminarSession ssession = new SeminarSession(session_name,mTrainingList);
                                    ssession.setSeminarSessionId(session_id);
                                    ssession.setSeminarSessionTitle(session_title);
                                    ssession.setSeminarSessionName(session_name);
                                    mMasterSeminarList.add(ssession);
                                }
                                else if(!mTrainingList.get(i-1).getmSessionId().equalsIgnoreCase(session_id)) {
                                    SeminarSession ssession = new SeminarSession();
                                    ssession.setSeminarSessionId(session_id);
                                    ssession.setSeminarSessionTitle(session_title);
                                    ssession.setSeminarSessionName(session_name);
                                    mMasterSeminarList.add(ssession);
                                }

                                mTrainingList.add(seminar_result);*/
                            }
                            Log.d(TAG, "MASTERSEMINAR" + mMasterSeminarList.size)


                            //  Log.d(TAG, "onListResponse: " + mTrainingList.size());

                            //     mTrainingRecycler.setAdapter(new TrainingAdapter(context,mTrainingList,mMasterSeminarList,TrainingActivity.this));
                            //
                            //final List<List<ParentListItem>> mainmasterlist = Arrays.asList(mMasterSeminarList);
                            /* mAdapter = new SeminarSessionAdapter(TrainingActivity.this, mMasterSeminarList);


                            mAdapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener() {
                                @UiThread
                                @Override
                                public void onParentExpanded(int position) {
                                    SeminarSession expandedMovieCategory = (SeminarSession) mMasterSeminarList.get(position);

                                    //String toastMsg = getResources().getString(R.string.expanded, expandedMovieCategory.getSeminarSessionTitle());
                                    Toast.makeText(TrainingActivity.this,
                                            expandedMovieCategory.getSeminarSessionTitle(),
                                            Toast.LENGTH_SHORT)
                                            .show();
                                }
                                @UiThread
                                @Override
                                public void onParentCollapsed(int position) {
                                    SeminarSession collapsedMovieCategory = (SeminarSession) mMasterSeminarList.get(position);

                                   // String toastMsg = getResources().getString(R.string.collapsed, collapsedMovieCategory.getName());
                                    Toast.makeText(TrainingActivity.this,
                                            collapsedMovieCategory.getSeminarSessionTitle(),
                                            Toast.LENGTH_SHORT)
                                            .show();
                                }
                            });

                            mTrainingRecycler.setAdapter(mAdapter);
                            mTrainingRecycler.setLayoutManager(new LinearLayoutManager(context));
                            */
                            val arrayAdapter: ArrayAdapter<*> = object : ArrayAdapter<Any?>(
                                this@TrainingActivity,
                                R.layout.simple_spinner_dropdown_item,
                                mSessionList as List<Any?>
                            ) {
                                override fun isEnabled(position: Int): Boolean {
                                    return if (position == 0) {
                                        false
                                    } else {
                                        true
                                    }
                                }

                                override fun getDropDownView(
                                    position: Int, convertView: View?,
                                    parent: ViewGroup
                                ): View {
                                    val view: View =
                                        super.getDropDownView(position, convertView, parent)
                                    val tv: TextView = view as TextView
                                    if (position == 0) {
                                        // Set the hint text color gray
                                        tv.setTextColor(Color.GRAY)
                                    } else {
                                        tv.setTextColor(Color.BLACK)
                                    }
                                    return view
                                }
                            }
                            arrayAdapter.setDropDownViewResource(R.layout.spinner_item)
                            session_spinner!!.setAdapter(arrayAdapter)
                            session_spinner!!.setOnItemSelectedListener(object :
                                AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    parent: AdapterView<*>?,
                                    view: View,
                                    position: Int,
                                    id: Long
                                ) {
                                    // Log.d(TAG, "onModelSelect: "+ response.body().getmResult().get(position).getmStallCategory());
                                    if (position > 0) {
                                        val seminarsessiontitle = mSessionList[position]
                                        Log.d(TAG, "onItemSelected: $seminarsessiontitle")
                                        val arrayString =
                                            seminarsessiontitle.split(":").toTypedArray()
                                        val sessioname = arrayString[0]
                                        val sessiondate = arrayString[1]
                                        if (mMasterSeminarList[position - 1].seminarSessionName.equals(sessioname,ignoreCase = true)

                                        ) {
                                            createspecificseminar(mMasterSeminarList[position - 1])
                                        }
                                    }
                                }

                                override fun onNothingSelected(parent: AdapterView<*>?) {}
                            })
                            createSeminarlist(mMasterSeminarList)
                            cdsession!!.setVisibility(View.VISIBLE)
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }



                } else {
                    mProgressBar!!.setVisibility(View.GONE)
                    mTrainingRecycler!!.setVisibility(View.VISIBLE)

                }


            } catch (e: JSONException) {
                println("Exception caught");
            }


        }.execute(
            "POST",
            ConstApi.LIVE_URL + "api.php?requestparm=user",
            jsonObject.toString().trim()
        )
//        val mRetrofit: Retrofit = Retrofit.Builder()
//            .baseUrl(ConstApi.LIVE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        val mSeminar: SeminarInterface = mRetrofit.create(SeminarInterface::class.java)
//        val call: Call<SeminarModel> = mSeminar.sendMethod(jsonObject)
//        call.enqueue(object : Callback<SeminarModel> {
//            override fun onResponse(call: Call<SeminarModel>, response: Response<SeminarModel>) {
//                if (response.isSuccessful()) {
//                    var countermaster = 0
//                    mProgressBar!!.setVisibility(View.GONE)
//                    mTrainingRecycler!!.setVisibility(View.VISIBLE)
//                    Log.d(TAG, "onResponse: " + response.body().getmSeminarMsg())
//                    val message: String? = response.body().getmSeminarMsg()
//                    if (message!!.contains("OK")) {
//                        Log.d(TAG, "ResultResponse: " + response.body().getmResultSeminar())
//                        val get_result: String? = response.body().getmResultSeminar()
//                        try {
//                            val jsonArray = JSONArray(get_result)
//                            for (i in 0 until jsonArray.length()) {
//                                val `object`: JSONObject = jsonArray.getJSONObject(i)
//                                val gson = Gson()
//                                val resultSeminar: ResultSeminar =
//                                    gson.fromJson(`object`.toString(), ResultSeminar::class.java)
//                                val seminar_id: String? = resultSeminar.getmSeminarId()
//                                val session_id: String? = resultSeminar.getmSessionId()
//                                val session_name: String? = resultSeminar.getmSessionName()
//                                val session_title: String? = resultSeminar.getmSessionTitle()
//                                val seminar_title: String? = resultSeminar.getmSeminarTitle()
//                                Log.d(TAG, "onEmminarResponse: $seminar_title")
//                                val seminar_owner: String? = resultSeminar.getmSeminarOwner()
//                                val seminar_date: String?= resultSeminar.getmSeminarDate()
//                                val seminar_start: String? = resultSeminar.getmSeminarStart()
//                                val seminar_end: String? = resultSeminar.getmSeminarEnd()
//                                // mSessionNameList.add(session_name);
//
//                                // SeminarSession mstrsessionname = (SeminarSession) mMasterSeminarList.get(countermaster -1).
//                                if (mMasterSeminarList.size == 0) {
//                                    val ssession = SeminarSession(session_name!!, mTrainingList)
//                                    ssession.seminarSessionId?.get(session_id!!.toInt())
//                                    ssession.seminarSessionTitle?.get(session_title!!.toInt())
//                                    ssession.seminarSessionName.get(session_name.toInt())
//                                    mMasterSeminarList.add(ssession)
//                                    mSessionList.add("$session_name:$seminar_date")
//                                    countermaster = countermaster + 1
//                                    val seminar_result = ResultSeminar()
//                                    seminar_result.setmSessionName(session_name)
//                                    seminar_result.setmSeminarId(seminar_id)
//                                    seminar_result.setmSeminarDate(seminar_date)
//                                    seminar_result.setmSessionTitle(session_title)
//                                    seminar_result.setmSeminarOwner(seminar_owner)
//                                    seminar_result.setmSeminarStart(seminar_start)
//                                    seminar_result.setmSeminarEnd(seminar_end)
//                                    seminar_result.setmSessionId(session_id)
//                                    seminar_result.setmSeminarTitle(seminar_title)
//                                    mTrainingList.add(seminar_result)
//                                } else if (mMasterSeminarList[countermaster - 1].seminarSessionName.equals(session_name,ignoreCase = true)
//
//                                ) {
//                                    val seminar_result = ResultSeminar()
//                                    seminar_result.setmSessionName(session_name)
//                                    seminar_result.setmSeminarId(seminar_id)
//                                    seminar_result.setmSeminarDate(seminar_date)
//                                    seminar_result.setmSessionTitle(session_title)
//                                    seminar_result.setmSeminarOwner(seminar_owner)
//                                    seminar_result.setmSeminarStart(seminar_start)
//                                    seminar_result.setmSeminarEnd(seminar_end)
//                                    seminar_result.setmSessionId(session_id)
//                                    seminar_result.setmSeminarTitle(seminar_title)
//                                    mTrainingList.add(seminar_result)
//                                } else if (!mMasterSeminarList[countermaster - 1].seminarSessionName.equals(session_name,ignoreCase = true)
//                                ) {
//                                    mTrainingList = ArrayList<ResultSeminar>()
//                                    val ssession = SeminarSession(session_name!!, mTrainingList)
//                                    ssession.seminarSessionId!!.get(session_id!!.toInt())
//                                    ssession.seminarSessionTitle!!.get(session_title!!.toInt())
//                                    ssession.seminarSessionName!!.get(session_name!!.toInt())
//                                    mMasterSeminarList.add(ssession)
//                                    mSessionList.add("$session_name:$seminar_date")
//                                    countermaster = countermaster + 1
//                                    val seminar_result = ResultSeminar()
//                                    seminar_result.setmSessionName(session_name)
//                                    seminar_result.setmSeminarId(seminar_id)
//                                    seminar_result.setmSeminarDate(seminar_date)
//                                    seminar_result.setmSessionTitle(session_title)
//                                    seminar_result.setmSeminarOwner(seminar_owner)
//                                    seminar_result.setmSeminarStart(seminar_start)
//                                    seminar_result.setmSeminarEnd(seminar_end)
//                                    seminar_result.setmSessionId(session_id)
//                                    seminar_result.setmSeminarTitle(seminar_title)
//                                    mTrainingList.add(seminar_result)
//                                }
//
//                                /*
//
//
//                                if(mTrainingList.size()==0)
//                                {
//                                    SeminarSession ssession = new SeminarSession(session_name,mTrainingList);
//                                    ssession.setSeminarSessionId(session_id);
//                                    ssession.setSeminarSessionTitle(session_title);
//                                    ssession.setSeminarSessionName(session_name);
//                                    mMasterSeminarList.add(ssession);
//                                }
//                                else if(!mTrainingList.get(i-1).getmSessionId().equalsIgnoreCase(session_id)) {
//                                    SeminarSession ssession = new SeminarSession();
//                                    ssession.setSeminarSessionId(session_id);
//                                    ssession.setSeminarSessionTitle(session_title);
//                                    ssession.setSeminarSessionName(session_name);
//                                    mMasterSeminarList.add(ssession);
//                                }
//
//                                mTrainingList.add(seminar_result);*/
//                            }
//                            Log.d(TAG, "MASTERSEMINAR" + mMasterSeminarList.size)
//
//
//                            //  Log.d(TAG, "onListResponse: " + mTrainingList.size());
//
//                            //     mTrainingRecycler.setAdapter(new TrainingAdapter(context,mTrainingList,mMasterSeminarList,TrainingActivity.this));
//                            //
//                            //final List<List<ParentListItem>> mainmasterlist = Arrays.asList(mMasterSeminarList);
//                            /* mAdapter = new SeminarSessionAdapter(TrainingActivity.this, mMasterSeminarList);
//
//
//                            mAdapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener() {
//                                @UiThread
//                                @Override
//                                public void onParentExpanded(int position) {
//                                    SeminarSession expandedMovieCategory = (SeminarSession) mMasterSeminarList.get(position);
//
//                                    //String toastMsg = getResources().getString(R.string.expanded, expandedMovieCategory.getSeminarSessionTitle());
//                                    Toast.makeText(TrainingActivity.this,
//                                            expandedMovieCategory.getSeminarSessionTitle(),
//                                            Toast.LENGTH_SHORT)
//                                            .show();
//                                }
//                                @UiThread
//                                @Override
//                                public void onParentCollapsed(int position) {
//                                    SeminarSession collapsedMovieCategory = (SeminarSession) mMasterSeminarList.get(position);
//
//                                   // String toastMsg = getResources().getString(R.string.collapsed, collapsedMovieCategory.getName());
//                                    Toast.makeText(TrainingActivity.this,
//                                            collapsedMovieCategory.getSeminarSessionTitle(),
//                                            Toast.LENGTH_SHORT)
//                                            .show();
//                                }
//                            });
//
//                            mTrainingRecycler.setAdapter(mAdapter);
//                            mTrainingRecycler.setLayoutManager(new LinearLayoutManager(context));
//                            */
//                            val arrayAdapter: ArrayAdapter<*> = object : ArrayAdapter<Any?>(
//                                this@TrainingActivity,
//                                R.layout.simple_spinner_dropdown_item,
//                                mSessionList as List<Any?>
//                            ) {
//                                override fun isEnabled(position: Int): Boolean {
//                                    return if (position == 0) {
//                                        false
//                                    } else {
//                                        true
//                                    }
//                                }
//
//                                override fun getDropDownView(
//                                    position: Int, convertView: View,
//                                    parent: ViewGroup
//                                ): View {
//                                    val view: View =
//                                        super.getDropDownView(position, convertView, parent)
//                                    val tv: TextView = view as TextView
//                                    if (position == 0) {
//                                        // Set the hint text color gray
//                                        tv.setTextColor(Color.GRAY)
//                                    } else {
//                                        tv.setTextColor(Color.BLACK)
//                                    }
//                                    return view
//                                }
//                            }
//                            arrayAdapter.setDropDownViewResource(R.layout.spinner_item)
//                            session_spinner!!.setAdapter(arrayAdapter)
//                            session_spinner!!.setOnItemSelectedListener(object :
//                                AdapterView.OnItemSelectedListener {
//                                override fun onItemSelected(
//                                    parent: AdapterView<*>?,
//                                    view: View,
//                                    position: Int,
//                                    id: Long
//                                ) {
//                                    // Log.d(TAG, "onModelSelect: "+ response.body().getmResult().get(position).getmStallCategory());
//                                    if (position > 0) {
//                                        val seminarsessiontitle = mSessionList[position]
//                                        Log.d(TAG, "onItemSelected: $seminarsessiontitle")
//                                        val arrayString =
//                                            seminarsessiontitle.split(":").toTypedArray()
//                                        val sessioname = arrayString[0]
//                                        val sessiondate = arrayString[1]
//                                        if (mMasterSeminarList[position - 1].seminarSessionName.equals(sessioname,ignoreCase = true)
//
//                                        ) {
//                                            createspecificseminar(mMasterSeminarList[position - 1])
//                                        }
//                                    }
//                                }
//
//                                override fun onNothingSelected(parent: AdapterView<*>?) {}
//                            })
//                            createSeminarlist(mMasterSeminarList)
//                            cdsession!!.setVisibility(View.VISIBLE)
//                        } catch (e: JSONException) {
//                            e.printStackTrace()
//                        }
//                    }
//                } else {
//                    mProgressBar!!.setVisibility(View.GONE)
//                    mTrainingRecycler!!.setVisibility(View.VISIBLE)
//                    Log.d(TAG, "onResponseError: " + response.errorBody().toString())
//                }
//            }
//
//            override fun onFailure(call: Call<SeminarModel>, t: Throwable) {
//                mProgressBar!!.setVisibility(View.GONE)
//                mTrainingRecycler!!.setVisibility(View.VISIBLE)
//                Log.d(TAG, "onFailure: $t")
//                if (t is SocketTimeoutException) {
//                    // "Connection Timeout";
//                    mRetryLayout!!.setVisibility(View.VISIBLE)
//                    mRetryButton!!.setOnClickListener(View.OnClickListener { getSeminar(this@TrainingActivity) })
//                    Toast.makeText(
//                        this@TrainingActivity,
//                        "Connection Timeout Please Retry",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                } else if (t is IOException) {
//                    // "Timeout";
//                    Toast.makeText(
//                        this@TrainingActivity,
//                        "Timeout Please Retry",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    mRetryLayout!!.setVisibility(View.VISIBLE)
//                    mRetryButton!!.setOnClickListener(View.OnClickListener { getSeminar(this@TrainingActivity) })
//                } else {
//                    //Call was cancelled by user
//                    if (call.isCanceled()) {
//                        Toast.makeText(
//                            this@TrainingActivity,
//                            "Call was cancelled forcefully",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    } else {
//                        //Generic error handling
//                        Toast.makeText(
//                            this@TrainingActivity,
//                            "Some Network Issue",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                }
//            }
//        })
    }

    var Isexpanded = false
    private val childsessionview: View? = null
    var childlayout: LinearLayout? = null
    private fun createSeminarlist(ProcessSeminarList: List<SeminarSession>) {
        val inflater: LayoutInflater = getLayoutInflater()
        for (i in ProcessSeminarList.indices) {
            val SeminarLayout: View = inflater.inflate(R.layout.list_item_seminar_parent, null)
            val tvSeminarSession: TextView =
                SeminarLayout.findViewById<TextView>(R.id.parent_item_seminar_title)
            tvSeminarSession.setText(ProcessSeminarList[i].seminarSessionName)
            /* final ImageView tvseminarclickdown = SeminarLayout.findViewById(R.id.parent_list_item_expand_arrow);
                 final ImageView tvseminarclickup = SeminarLayout.findViewById(R.id.parent_list_item_collapse_arrow);
                    */
            // tvseminarclick.setOnClickListener
            /* tvseminarclickdown.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v)
                       {
                           tvseminarclickup.setVisibility(View.VISIBLE);
                           tvseminarclickdown.setVisibility(View.GONE);
                       }
                   });

                   tvseminarclickup.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v)
                       {
                           tvseminarclickup.setVisibility(View.GONE);
                           tvseminarclickdown.setVisibility(View.VISIBLE);
                       }
                   });

                   SeminarLayout.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v)
                       {

                          if(ProcessSeminarList.get(pst).isInitiallyExpanded()) {
                              tvseminarclickup.setVisibility(View.GONE);
                              tvseminarclickdown.setVisibility(View.VISIBLE);
                        //  processseminarchild(ProcessSeminarList.get(pst).getSeminarParticularSessionItem());

                          }
                          else {
                              tvseminarclickup.setVisibility(View.VISIBLE);
                              tvseminarclickdown.setVisibility(View.GONE);
                              childsessionview.setVisibility(View.VISIBLE);
                          }

                           Toast.makeText(TrainingActivity.this,ProcessSeminarList.get(pst).getSeminarSessionId(), Toast.LENGTH_SHORT)
                           .show();


                       }
                   });*/llseminarmainheadingsession!!.addView(SeminarLayout)
            val inflaterseminarchild: LayoutInflater = getLayoutInflater()
            for (c in 0 until ProcessSeminarList[i].seminarParticularSessionItem.size) {
                val Seminarchilditem: View =
                    inflaterseminarchild.inflate(R.layout.training_session_layout, null)
                // TextView seminarsessiontitle = Seminarchilditem.findViewById(R.id.child_list_item_crime_date_text_view);
                // seminarsessiontitle.setText(ProcessSeminarList.get(pst).getSeminarParticularSessionItem().get(c).getmSeminarTitle());
                val mSeminarName: TextView =
                    Seminarchilditem.findViewById<TextView>(R.id.seminar_name)
                // mSeminarName.setVisibility(View.GONE);
                val mSeminarTitle: TextView =
                    Seminarchilditem.findViewById<TextView>(R.id.seminar_title)
                mSeminarTitle.setText(
                    ProcessSeminarList[i].seminarParticularSessionItem.get(c)
                        .getmSeminarTitle()
                )
                val mSeminarOwnerTxt: TextView =
                    Seminarchilditem.findViewById<TextView>(R.id.seminar_ownertxt)
                val mSeminarOwner: TextView =
                    Seminarchilditem.findViewById<TextView>(R.id.seminar_owner)
                mSeminarOwner.setText(
                    ProcessSeminarList[i].seminarParticularSessionItem.get(c)
                        .getmSeminarOwner()
                )
                val mSeminarDateTxt: TextView =
                    Seminarchilditem.findViewById<TextView>(R.id.seminar_datetxt)
                val mSeminarDate: TextView =
                    Seminarchilditem.findViewById<TextView>(R.id.seminar_date)
                mSeminarDate.setText(
                    ProcessSeminarList[i].seminarParticularSessionItem.get(c).getmSeminarDate()
                )
                val mSeminarStartTxt: TextView =
                    Seminarchilditem.findViewById<TextView>(R.id.seminar_starttxt)
                val mSeminarStart: TextView =
                    Seminarchilditem.findViewById<TextView>(R.id.seminar_start)
                mSeminarStart.setText(
                    ProcessSeminarList[i].seminarParticularSessionItem.get(c)
                        .getmSeminarStart()
                )
                val mSeminarEndTxt: TextView =
                    Seminarchilditem.findViewById<TextView>(R.id.seminar_endtxt)
                val mSeminarEnd: TextView =
                    Seminarchilditem.findViewById<TextView>(R.id.seminar_end)
                mSeminarEnd.setText(
                    ProcessSeminarList[i].seminarParticularSessionItem.get(c).getmSeminarEnd()
                )
                val btnsendquery = Seminarchilditem.findViewById<Button>(R.id.btnsendquery)
                btnsendquery.setOnClickListener { /* mContext.startActivity(new Intent(mContext, TrainingQueryActivity.class)
                                       .putExtra("seminar_name",mTrainingList.get(position).getmSessionName())
                                       .putExtra("seminar_title",mTrainingList.get(position).getmSessionTitle())
                                       .putExtra("seminar_owner",mTrainingList.get(position).getmSeminarOwner()));
                               mContext.overridePendingTransition(R.anim.slidein_right, R.anim.slide_out_left);
                            */
                    val myIntent = Intent(this@TrainingActivity, TrainingQueryActivity::class.java)
                    myIntent.putExtra(
                        "seminar_name",
                        ProcessSeminarList[i].seminarParticularSessionItem.get(c)
                            .getmSessionName()
                    )
                    myIntent.putExtra(
                        "seminar_title",
                        ProcessSeminarList[i].seminarParticularSessionItem.get(c)
                            .getmSeminarTitle()
                    )
                    myIntent.putExtra(
                        "seminar_owner",
                        ProcessSeminarList[i].seminarParticularSessionItem.get(c)
                            .getmSeminarOwner()
                    )
                    myIntent.putExtra(
                        "seminar_id",
                        ProcessSeminarList[i].seminarParticularSessionItem.get(c)
                            .getmSeminarId()
                    )
                    startActivity(myIntent)
                    overridePendingTransition(R.anim.slidein_right, R.anim.slide_out_left)
                }
                llseminarmainheadingsession!!.addView(Seminarchilditem)
            }
        }
    }

    private fun createspecificseminar(ProcessParticularSeminar: SeminarSession) {
        if (llseminarmainheadingsession != null) {
            llseminarmainheadingsession!!.removeAllViews()
            val inflater: LayoutInflater = getLayoutInflater()
            val SeminarLayout: View = inflater.inflate(R.layout.list_item_seminar_parent, null)

            // final int pst = i;
            val tvSeminarSession: TextView =
                SeminarLayout.findViewById<TextView>(R.id.parent_item_seminar_title)
            tvSeminarSession.setText(ProcessParticularSeminar.seminarSessionName)
            llseminarmainheadingsession!!.addView(SeminarLayout)
            val inflaterseminarchild: LayoutInflater = getLayoutInflater()
            for (c in 0 until ProcessParticularSeminar.seminarParticularSessionItem.size) {
                val Seminarchilditem: View =
                    inflaterseminarchild.inflate(R.layout.training_session_layout, null)
                val mSeminarName: TextView =
                    Seminarchilditem.findViewById<TextView>(R.id.seminar_name)
                // mSeminarName.setVisibility(View.GONE);
                val mSeminarTitle: TextView =
                    Seminarchilditem.findViewById<TextView>(R.id.seminar_title)
                mSeminarTitle.setText(
                    ProcessParticularSeminar.seminarParticularSessionItem.get(c)
                        .getmSeminarTitle()
                )
                val mSeminarOwnerTxt: TextView =
                    Seminarchilditem.findViewById<TextView>(R.id.seminar_ownertxt)
                val mSeminarOwner: TextView =
                    Seminarchilditem.findViewById<TextView>(R.id.seminar_owner)
                mSeminarOwner.setText(
                    ProcessParticularSeminar.seminarParticularSessionItem.get(c)
                        .getmSeminarOwner()
                )
                val mSeminarDateTxt: TextView =
                    Seminarchilditem.findViewById<TextView>(R.id.seminar_datetxt)
                val mSeminarDate: TextView =
                    Seminarchilditem.findViewById<TextView>(R.id.seminar_date)
                mSeminarDate.setText(
                    ProcessParticularSeminar.seminarParticularSessionItem.get(c)
                        .getmSeminarDate()
                )
                val mSeminarStartTxt: TextView =
                    Seminarchilditem.findViewById<TextView>(R.id.seminar_starttxt)
                val mSeminarStart: TextView =
                    Seminarchilditem.findViewById<TextView>(R.id.seminar_start)
                mSeminarStart.setText(
                    ProcessParticularSeminar.seminarParticularSessionItem.get(c)
                        .getmSeminarStart()
                )
                val mSeminarEndTxt: TextView =
                    Seminarchilditem.findViewById<TextView>(R.id.seminar_endtxt)
                val mSeminarEnd: TextView =
                    Seminarchilditem.findViewById<TextView>(R.id.seminar_end)
                mSeminarEnd.setText(
                    ProcessParticularSeminar.seminarParticularSessionItem.get(c)
                        .getmSeminarEnd()
                )
                val btnsendquery = Seminarchilditem.findViewById<Button>(R.id.btnsendquery)
                btnsendquery.setOnClickListener { /* mContext.startActivity(new Intent(mContext, TrainingQueryActivity.class)
                                       .putExtra("seminar_name",mTrainingList.get(position).getmSessionName())
                                       .putExtra("seminar_title",mTrainingList.get(position).getmSessionTitle())
                                       .putExtra("seminar_owner",mTrainingList.get(position).getmSeminarOwner()));
                               mContext.overridePendingTransition(R.anim.slidein_right, R.anim.slide_out_left);
                            */
                    val myIntent = Intent(this@TrainingActivity, TrainingQueryActivity::class.java)
                    myIntent.putExtra(
                        "seminar_name",
                        ProcessParticularSeminar.seminarParticularSessionItem.get(
                            c
                        ).getmSessionName()
                    )
                    myIntent.putExtra(
                        "seminar_title",
                        ProcessParticularSeminar.seminarParticularSessionItem.get(
                            c
                        ).getmSeminarTitle()
                    )
                    myIntent.putExtra(
                        "seminar_owner",
                        ProcessParticularSeminar.seminarParticularSessionItem.get(
                            c
                        ).getmSeminarOwner()
                    )
                    myIntent.putExtra(
                        "seminar_id",
                        ProcessParticularSeminar.seminarParticularSessionItem.get(
                            c
                        ).getmSeminarId()
                    )
                    startActivity(myIntent)
                    overridePendingTransition(R.anim.slidein_right, R.anim.slide_out_left)
                }
                llseminarmainheadingsession!!.addView(Seminarchilditem)
            }
        }
    }

    companion object {
        private val TAG = TrainingActivity::class.java.simpleName
        var mQuerylayout: LinearLayout? = null
        private const val INITIAL_POSITION = 0.0f
        private const val ROTATED_POSITION = 180f
    }
}