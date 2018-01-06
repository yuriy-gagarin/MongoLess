package a14e.bson.decoder

import a14e.bson.{Bson, ID}
import a14e.bson._
import a14e.bson.auto._
import org.scalatest.{FlatSpec, Matchers}
import BsonDecoder._

case class SampleUser(id: ID[Int],
                      name: String,
                      job: Option[Job],
                      children: Seq[SampleUser])

case class Job(company: String,
               salary: Long)

class GenericBsonDecodersSpec extends FlatSpec with Matchers {

  "GenericDecoders" should "encode simple class" in {
    val user = SampleUser(
      id = 213,
      name = "name",
      job = None,
      children = Seq.empty
    )

    val bson = Bson.obj(
      "_id" -> 213,
      "name" ->  "name",
      "children" -> Bson.arr()
    )

    bson.as[SampleUser] shouldBe user
  }

  it should "encode nested classes" in {
    val user = SampleUser(
      id = 213,
      name = "name",
      job = Some(
        Job(
          company = "some company",
          salary = 123
        )
      ),
      children = Seq.empty
    )

    val bson = Bson.obj(
      "_id" -> 213,
      "name" ->  "name",
      "job" -> Bson.obj(
        "company" -> "some company",
        "salary" -> 123L
      ),
      "children" -> Bson.arr()
    )

    bson.as[SampleUser] shouldBe user
  }

  it should "encode recourcive classes" in {
    val user = SampleUser(
      id = 213,
      name = "name",
      job = None,
      children = Seq(
        SampleUser(
          id = 456,
          name = "name1",
          job = None,
          children = Seq.empty
        )
      )
    )

    val bson = Bson.obj(
      "_id" -> 213,
      "name" ->  "name",
      "children" -> Bson.arr(
        Bson.obj(
          "_id" -> 456,
          "name" ->  "name1",
          "children" -> Bson.arr()
        )
      )
    )

    bson.as[SampleUser] shouldBe user
  }
}