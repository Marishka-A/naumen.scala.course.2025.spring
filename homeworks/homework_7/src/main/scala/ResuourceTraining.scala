package ru.dru

import zio.{IO, Scope, ZIO, ZIOAppArgs, ZIOAppDefault}

import java.io.{BufferedReader, BufferedWriter, FileReader, FileWriter}


/**
 * Необходимо реализовать функции readData и writeData, записывающие и читающие данные в/из файла соответственно.
 * В реализации следует применять безопасное использование ресурсов ZIO.acquireReleaseWith
 */


object ResuourceTraining extends ZIOAppDefault {

  def readData(filePath: String): IO[Throwable, String] = ZIO.acquireReleaseWith(
    ZIO.attempt(new BufferedReader(new FileReader(filePath))))(data =>
    ZIO.attempt(data.close()).orDie)(data => ZIO.attempt(data.readLine()))

  def writeData(filePath: String, data: String): ZIO[Any, Nothing, Unit] = ZIO.acquireReleaseWith(ZIO.succeed(
    new BufferedWriter(new FileWriter(filePath, false))))(writer =>
    ZIO.succeed(writer.close()).ignore)(writer => ZIO.attempt(writer.write(data)).map(_ => writer.flush()).ignore)

  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = ZIO.succeed("Done")
}
