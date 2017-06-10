10.06.2017
NetCracker courses
Multithreading Task
Designer: student Yakimov Denis

Task:
File Reader system, that reads automatically created file and in three threads creates three files that contains only special characters(like alphabetic/numeric/signs) in new files. Fourth thread contains specialized information about other three threads like quantity of valid characters for one task, name of thread, starting time and ending time of task.

Dependencies:
I want to note that this project is a maven-build project. The cause of this is Apache-commons library that is needed for a proper work(Especially for RandomStringUtils.randomAscii method). Please load the dependencies for this maven project!

Files:
FileCreator - interface for creating random file.
FileCreatorFactory - interface for factory that will create file creator.
FileStatisticsReader - main interface that will do the job.
FileStatisticsReaderSupport - special interface for holding CharacterEqualiators - special classes for character reading.
FileStatisticsBuilder - builder for insertion of support class in file statistics reader.
FileStatisticsFactory - factory for creation of file statistics reader.
FileStatisticsReaderSupportFactory - factory for creation support class.
CharacterEqualiator - special interface for reading characters, must be implemented.
NoFileStatisticReaderSupportException - runtime exception for a case when support class is not presented.
All "...Impl" files are working implementations of the listed interfaces.
Package "logging" contains special class for configuring logger.
And package "tasks":
StatisticTask - class for gathering statistics using Exchange<> mechanism.
CharactedReadingTask - Runnable task that holds CharacterEqualiator and do the work.
ReaderTaskDataHolder - holder class that holds exchanger and name of the thread in statistics task.
ReaderTaskExchangeData - simple data holder for exchanging information from thread to thread in runtime.