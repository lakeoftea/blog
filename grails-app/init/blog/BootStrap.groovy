package blog

import javax.servlet.ServletContext

class BootStrap {

    def init = { ServletContext servletContext ->

        File image1 = new File(servletContext.getResource("/images/Music-Note.jpg").toURI())
        File image2 = new File(servletContext.getResource("/images/staunton-chess-set-1.jpg").toURI())
        File image3 = new File(servletContext.getResource("/images/ThinkstockPhotos-494037394.jpg").toURI())

        new Tag(name: "music", description: "music is the best outlit", imageBytes: image1.bytes, imageName: image1.name, imageContentType: "image/jpg").save(failOnError:true)
        new Tag(name: "chess programming", description: "chess programming is interesting", imageBytes: image2.bytes, imageName: image2.name, imageContentType: "image/jpg").save(failOnError:true)
        new Tag(name: "oranges", description: "oranges have a great aroma and taste great too", imageBytes: image3.bytes, imageName: image3.name, imageContentType: "image/jpg").save(failOnError:true)

    }

    def destroy = {
    }
}
