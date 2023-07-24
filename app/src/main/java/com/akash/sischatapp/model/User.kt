package com.akash.sischatapp.model

class User {
    var uid: String? = null
    var fullName: String? = null
    var userName: String? = null
    var bio: String? = null
    var phoneNumber: String? = null
    var profileImage: String? = null

    constructor() {}
    constructor(
        uid: String?,
        fullName: String?,
        userName: String?,
        bio: String?,
        phoneNumber: String?,
        profileImage: String?
    ) {
        this.uid = uid
        this.fullName = fullName
        this.userName = userName
        this.bio = bio
        this.phoneNumber = phoneNumber
        this.profileImage = profileImage
    }
}