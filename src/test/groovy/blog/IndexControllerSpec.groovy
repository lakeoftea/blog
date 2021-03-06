package blog

import grails.testing.gorm.DataTest
import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

import javax.servlet.ServletContext

class IndexControllerSpec extends Specification implements ControllerUnitTest<IndexController>, DataTest{
    File image1
    File image2
    File image3
    File image4
    File image5
    File image6
    File image7
    Post post1
    Post post2
    Post post3
    Tag tag1
    Tag tag2
    Tag tag3

    void setupSpec(){
        mockDomain Tag
        mockDomain Post
        System.metaClass.static.getProperty = { String key ->
            System.setProperty("BLOG_CONFIG", ServletContext.getResource("/blog.config").file)
        }
    }

    void cleanupSpec(){
        System.metaClass = null
    }

    void setup(){

        image1 = new File(servletContext.getResource("/images/Music-Note.jpg").toURI())
        image2 = new File(servletContext.getResource("/images/staunton-chess-set-1.jpg").toURI())
        image3 = new File(servletContext.getResource("/images/ThinkstockPhotos-494037394.jpg").toURI())
        image4 = new File(servletContext.getResource("/images/800px_COLOURBOX10725277.jpg").toURI())
        image5 = new File(servletContext.getResource("/images/d_SC_DETAIL_MODULE1_720x690_2.2_LowerBackPain.jpg").toURI())
        image6 = new File(servletContext.getResource("/images/hooked-on-code_icon_logo_RGB.png").toURI())
        image7 = new File(servletContext.getResource("/images/images.png").toURI())


        tag1 = new Tag(enabled: true, shortUrl: "music-times", name: "music", description: "music is the best outlit", imageBytes: image1.bytes, imageName: image1.name, imageContentType: "image/jpg").save(failOnError:true)
        tag2 = new Tag(enabled: true, shortUrl: "chess-programming", name: "chess programming", description: "chess programming is interesting", imageBytes: image2.bytes, imageName: image2.name, imageContentType: "image/jpg").save(failOnError:true)
        tag3 = new Tag(enabled: true, shortUrl:"oranges", name: "oranges", description: "oranges have a great aroma and taste great too", imageBytes: image3.bytes, imageName: image3.name, imageContentType: "image/jpg").save(failOnError:true)

        post1 = new Post(title: "Fallout VR 4 On Occulus Rift", content: "Fallout 4 on PC with occulus rift is a fun experience.  Remember how freaked out you were when those radioactive zombies came running right at you?  It's even more fun in VR!",
                summary: "A summary of my experiences with running Fallout 4 VR on occulus rift.  The unofficially unsupported experience!", shortUrl: "occulus-fallout-4-vr", enabled: true,
                imageBytes: image4.bytes, imageContentType: "image/jpg", imageName: "800px_COLOURBOX10725277.jpg")

        post1.addToTags(tag1)
        post1.addToTags(tag3)

        post2 = new Post(title: "Chairs.  Which ones are best for your back?", content: "Recently I was shopping at the Lumbar Yard, looking for different furniture that'll be good for my back.  I need a new bed and chair and a couch and need lower back support.  The employees were very helpful, now it's your turn!",
                summary: "Maintaining a healthy lower back is key to a long and fulfilled life.  Most successful people will admit that their lower back was their key to success.  Why can't it be yours too?  Check out this post for more.", shortUrl: "lower-back-and-you", enabled: true,
                imageBytes: image5.bytes, imageContentType: "image/jpg", imageName: "d_SC_DETAIL_MODULE1_720x690_2.2_LowerBackPain.jpg")

        post2.addToTags(tag2)

        post3 = new Post(title: "Programming Languages and Business Needs", content: "Every organization at some point must decide which languages best suite their business needs.  For some this is javascript, for others this is QBasic, and for others VBA and spreadsheet macros are enough to get through the quarter.",
                summary: "Each programming language has traits and characteristics that may make it particularly suitable for a particular task.  We'll consider some of those in this article", shortUrl: "programming-languages-business-needs", enabled: true,
                imageBytes: image6.bytes, imageContentType: "image/png", imageName: "hooked-on-code_icon_logo_RGB.png")
        post3.addToTags(tag3)
        post3.addToTags(tag2)
        post3.addToTags(tag1)
    }

    def savePostsAndTags(){
        tag1.save(flush:true, failOnError: true)
        tag2.save(flush:true, failOnError: true)
        tag3.save(flush:true, failOnError: true)
        post1.save(flush:true, failOnError: true)
        post2.save(flush:true, failOnError: true)
        post3.save(flush:true, failOnError: true)
    }

    void "does index return title, tagline, tags and posts, author stuff"(){
        when:
        savePostsAndTags()
        controller.index()
        then:
        model.posts.size() == 3
        model.tags.size() == 3
        model.title == "My blog"
        model.tagline == "Life's a Journey!"
        model.github == "http://github.com/me"
        model.twitter == "http://twitter.com/me"
        model.linkedin == "http://linkdin.com/me"
        model.htmlTitle == "cool blog"
    }

    void "does byTag return tag by shortUrl?"(){
        when:
        savePostsAndTags()
        params.shortUrl = "music-times"
        controller.byTag()
        then:
        model.tag.name == "music"
        model.posts.size() == 2
        model.title == "My blog"
        model.tagline == "Life's a Journey!"
        model.github == "http://github.com/me"
        model.twitter == "http://twitter.com/me"
        model.linkedin == "http://linkdin.com/me"
        model.htmlTitle == "cool blog"
    }

    void "does byPostShortUrl return post associated with ShortUrl"(){
        when:
        savePostsAndTags()
        params.shortUrl = "programming-languages-business-needs"
        controller.byPostShortUrl()
        then:
        model.title == "My blog"
        model.tagline == "Life's a Journey!"
        model.post.title == "Programming Languages and Business Needs"
        model.github == "http://github.com/me"
        model.twitter == "http://twitter.com/me"
        model.linkedin == "http://linkdin.com/me"
        model.htmlTitle == "cool blog"
    }

    void "are posts that are not enabled not returned?"(){
        when:
        post1.enabled = false
        post2.enabled = false
        post3.enabled = false
        savePostsAndTags()
        controller.index()
        then:
        model.posts.size() == 0
        flash.message == "No posts enabled, come back later :("
        flash.title == "Opps!"
        flash.class == "alert alert-warning"
    }

    void "if post is not enabled, is it not displayed on byTag"(){
        when:
        post1.enabled = false
        post3.enabled = false
        savePostsAndTags()
        params.shortUrl = "music-times"
        controller.byTag()
        then:
        model.posts.size() == 0
        flash.message == "No posts enabled with this tag :("
        flash.title == "Opps!"
        flash.class == "alert alert-warning"
    }

    void "does byPostShortUrl not show post when post is not enabled "(){
        when:
        post3.enabled = false
        savePostsAndTags()
        params.shortUrl = "programming-languages-business-needs"
        controller.byPostShortUrl()
        then:
        model.post == null
        flash.message == "Post not found :("
        flash.title == "Opps!"
        flash.class == "alert alert-warning"
    }

    void "does index not show disabled tags"(){
        when:
        tag2.enabled = false
        savePostsAndTags()
        controller.index()
        then:
        model.posts.size() == 2
    }

    void "are posts sorted by last modified date?"(){
        when:
        post3.enabled = false
        savePostsAndTags()

        sleep(1000)
        post2.save(flush: true)

        println(post1.lastUpdated)
        println(post2.lastUpdated)


        controller.index()
        then:
        model.posts.size() == 2
        model.posts[1].title == "Fallout VR 4 On Occulus Rift"
        model.posts[0].title == "Chairs.  Which ones are best for your back?"
    }

}
