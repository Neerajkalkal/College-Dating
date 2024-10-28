package com.example.collegedating.tokenmanagement

import com.example.collegedating.model.Token

object TokenOperation {
    fun saveToken(
        tokenManagement: TokenManagement,
        token: Token
    ){
        tokenManagement.saveRefreshToken(token.refreshToken)
        tokenManagement.saveAccessToken(token.accessToken)
    }

  fun saveSteps(
      tokenManagement: TokenManagement,
      step:Int
  ){
      tokenManagement.newUser(step)
  }



}