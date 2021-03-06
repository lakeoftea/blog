package blog

class Tag {

    String name
    String description
    String shortUrl
    Date dateCreated
    Date lastUpdated
    Boolean enabled
    byte[] imageBytes
    String imageName
    String imageContentType


    static constraints = {
        name blank:false
        description blank:false
        imageBytes blank:false
        enabled blank:false
        shortUrl blank:false
        imageName blank:false
        imageContentType blank:false
    }
    static mapping = {
        imageBytes sqlType: "longblob"
        description sqlType: "varchar(10000)"
    }

}
