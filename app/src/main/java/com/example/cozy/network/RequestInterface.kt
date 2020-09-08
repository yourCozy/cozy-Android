package com.example.cozy.network

import com.example.cozy.network.requestData.RequestLogin
import com.example.cozy.network.responseData.*
import retrofit2.Call
import retrofit2.http.*

interface RequestInterface {

    //소셜로그인 https://github.com/yourCozy/cozy-Server/wiki/%EC%86%8C%EC%85%9C%EB%A1%9C%EA%B7%B8%EC%9D%B8(%EC%82%AC%EC%9A%A9%EC%9E%90-%EC%A0%95%EB%B3%B4-%EC%A0%80%EC%9E%A5)
    @POST("/auth/social")
    fun requestLogin(@Body body: RequestLogin) : Call<ResponseSignin>

    //추천
    //책방 8개 추천 https://github.com/yourCozy/cozy-Server/wiki/%EC%B1%85%EB%B0%A9-8%EA%B0%9C-%EC%B6%94%EC%B2%9C
    //@GET("/bookstore/recommendation")
    //fun requestRecommendation(@HeaderMap headers: Map<String, String?>) : Call<ResponseRecommendList>

    //책방 상세 https://github.com/yourCozy/cozy-Server/wiki/%EC%B1%85%EB%B0%A9-%EC%83%81%EC%84%B8
    //@GET("/bookstore/detail/{bookstoreIdx}")


    //책방 상세_책방 피드 https://github.com/yourCozy/cozy-Server/wiki/%EC%B1%85%EB%B0%A9-%EC%83%81%EC%84%B8_%EC%B1%85%EB%B0%A9-%ED%94%BC%EB%93%9C
    //@GET("/feed/{bookstoreIdx}")


    //책방 상세_활동 피드 https://github.com/yourCozy/cozy-Server/wiki/%EC%B1%85%EB%B0%A9-%EC%83%81%EC%84%B8_%ED%99%9C%EB%8F%99-%ED%94%BC%EB%93%9C
    //@GET("/activity/{bookstoreIdx}")


    //지역
    //지역별 책방 조회 https://github.com/yourCozy/cozy-Server/wiki/%EC%A7%80%EC%97%AD%EB%B3%84-%EC%B1%85%EB%B0%A9-%EC%A1%B0%ED%9A%8C
    @GET("/bookstore/section/{sectionIdx}")
    fun requestMapLogin(@Path("sectionIdx") sectionIdx: Int, @HeaderMap headers: Map<String, String?>) : Call<ResponseMap>
    //로그인하지 않았을 때
    @GET("/bookstore/section/{sectionIdx}")
    fun requestMap(@Path("sectionIdx") sectionIdx: Int) : Call<ResponseMap>

    //지역별 책방 갯수 https://github.com/yourCozy/cozy-Server/wiki/%EC%A7%80%EC%97%AD%EB%B3%84-%EC%B1%85%EB%B0%A9-%EA%B0%AF%EC%88%98-%EB%B3%B4%EA%B8%B0
    @GET("/bookstore/count/section")
    fun requestCount(@HeaderMap headers: Map<String, String?>) : Call<ResponseCount>



    //활동
    //책방별 활동 조회 https://github.com/yourCozy/cozy-Server/wiki/%EC%B1%85%EB%B0%A9-%EC%83%81%EC%84%B8_%ED%99%9C%EB%8F%99-%ED%94%BC%EB%93%9C
    //@GET("/activity/{bookstoreIdx}")


    //활동 상세 조회 https://github.com/yourCozy/cozy-Server/wiki/%ED%99%9C%EB%8F%99-%EC%83%81%EC%84%B8-%EC%A1%B0%ED%9A%8C
    //@GET("/activity/detail/{activityIdx}")


    //카테고리별 활동 조회(마감 임박순) https://github.com/yourCozy/cozy-Server/wiki/%EC%B9%B4%ED%85%8C%EA%B3%A0%EB%A6%AC%EB%B3%84-%ED%99%9C%EB%8F%99-%EC%A1%B0%ED%9A%8C(%EB%A7%88%EA%B0%90-%EC%9E%84%EB%B0%95%EC%88%9C)
    //@GET("/activity/category/deadline/{categoryIdx}")


    //카테고리별 활동 조회(최신순)


    //내정보
    //관심 책방 조회 https://github.com/yourCozy/cozy-Server/wiki/%EA%B4%80%EC%8B%AC%EC%B1%85%EB%B0%A9-%EC%A1%B0%ED%9A%8C
    @GET("/mypage/interest")
    fun requestInterest(@HeaderMap headers: Map<String, String?>) : Call<ResponseInterest>


    //북마크 업데이트 https://github.com/yourCozy/cozy-Server/wiki/%EB%B6%81%EB%A7%88%ED%81%AC-%EC%97%85%EB%8D%B0%EC%9D%B4%ED%8A%B8
    @PUT("/mypage/interest/{bookstoreIdx}")
    fun requestBookmarkUpdate(@Path("bookstoreIdx") bookstoreIdx : Int, @HeaderMap headers: Map<String, String?>) : Call<ResponseBookmarkUpdate>


    //최근 본 책방 조회 https://github.com/yourCozy/cozy-Server/wiki/%EC%B5%9C%EA%B7%BC-%EB%B3%B8-%EC%B1%85%EB%B0%A9-%EC%A1%B0%ED%9A%8C
    //@GET("/mypage/recent")


    //프로필 사진 업데이트 https://github.com/yourCozy/cozy-Server/wiki/%ED%94%84%EB%A1%9C%ED%95%84-%EC%82%AC%EC%A7%84-%EC%97%85%EB%8D%B0%EC%9D%B4%ED%8A%B8
    //@POST("/user/profile")


    //취향 등록 https://github.com/yourCozy/cozy-Server/wiki/%EC%B7%A8%ED%96%A5-%EB%93%B1%EB%A1%9D
    //@POST("/mypage/recommendation?opt=%&opt=% ...")


    //취향 수정 https://github.com/yourCozy/cozy-Server/wiki/%EC%B7%A8%ED%96%A5-%EC%88%98%EC%A0%95
    //@PUT("/mypage/recommendation?opt=%&opt=% ...")

}



