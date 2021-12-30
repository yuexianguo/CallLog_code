package com.phone.base.file

data class PhoneBookInfo (
    var phoneList:ArrayList<PhoneBookItem>
)

class PhoneBookItem(
     var department:String,
     var phoneNumer:String
)