package com.example.collegedating.model.adduser

data class BasicProfileDetails(
    val firstName: String,
    val lastName: String,
    val dob: String,
    val collegeId: Int,
    val collegeName: String,
    val image: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BasicProfileDetails

        if (firstName != other.firstName) return false
        if (lastName != other.lastName) return false
        if (dob != other.dob) return false
        if (collegeId != other.collegeId) return false
        if (collegeName != other.collegeName) return false
        if (!image.contentEquals(other.image)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = firstName.hashCode()
        result = 31 * result + lastName.hashCode()
        result = 31 * result + dob.hashCode()
        result = 31 * result + collegeId
        result = 31 * result + collegeName.hashCode()
        result = 31 * result + image.contentHashCode()
        return result
    }
}
