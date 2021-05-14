package com.example.notelite.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

//Sets up databae
@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun dao(): Dao

    //Callback used to apply dummy data for testing
    class Callback @Inject constructor(
        private val database: Provider<NoteDatabase>,
        private val applicationScope: CoroutineScope
    ): RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val dao = database.get().dao()

            //Applies dummy data for testing purposes - just some random public figure's names.
            //Remove comment if you wish to use this pre-set data
            /*applicationScope.launch {
                dao.addNote(NoteEntity(
                    title = "Elon Musk",
                    text = "Elon Reeve Musk is a business magnate, industrial designer, and engineer." +
                            " He is the founder, CEO, CTO, and chief designer of SpaceX"
                ))
                dao.addNote(NoteEntity(
                    title = "Joe Rogan",
                    text = "Joseph James Rogan is an American comedian, podcast host, and UFC color " +
                            "commentator. He has also worked as a television host and an actor. Rogan" +
                            " began his career in comedy in August 1988 in the Boston area. After " +
                            "relocating to Los Angeles in 1994, he signed an exclusive developmental " +
                            "deal with Disney, and appeared as an actor on several television shows " +
                            "including Hardball and NewsRadio. In 1997, he started working for the " +
                            "Ultimate Fighting Championship (UFC) as an interviewer and color commentator. " +
                            "Rogan released his first comedy special in 2000. From 2001 to 2006, he " +
                            "was the host of Fear Factor. In 2009, Rogan launched his podcast The " +
                            "Joe Rogan Experience, which led him to increased success and podcast " +
                            "superstardom.[5] Joseph James Rogan was born on August 11, 1967, in " +
                            "Newark, New Jersey.[6][7] His grandfather had moved his family there " +
                            "in the 1940s.[8] He is of three-quarters Italian and one-quarter Irish " +
                            "descent.[9] His father, Joseph, is a former police officer in Newark. " +
                            "Rogan's parents divorced when he was five,[10] and he has not been in " +
                            "contact with his father since he was seven. Rogan recalled: \"All I " +
                            "remember of my dad are these brief, violent flashes of domestic violence" +
                            " ... But I don't want to complain about my childhood. Nothing bad ever " +
                            "really happened to me ... I don't hate the guy.\"[10] From ages 7 to 11," +
                            " the family lived in San Francisco, California,[10] followed by a move to " +
                            "Gainesville, Florida when he was eleven.[11] They settled outside Boston" +
                            " in Newton Upper Falls, Massachusetts, where Rogan attended Newton South" +
                            " High School,[12][13] from which he graduated in 1985.[14]"
                ))
                dao.addNote(NoteEntity(
                    title = "Jordan Peterson",
                    text = "Jordan Bernt Peterson (born 12 June 1962) is a Canadian professor of" +
                            " psychology at the University of Toronto, a clinical psychologist, " +
                            "and YouTube personality."
                ))
                dao.addNote(NoteEntity(
                    title = "Vladimir Putin",
                    text = "Vladimir Vladimirovich Putin (born 7 October 1952) is a Russian politician" +
                            " and former intelligence officer who is serving as the current president" +
                            " of Russia since 2012, previously being in the office from 1999 until 2008."
                ))
                dao.addNote(NoteEntity(
                    title = "Dalai Lama",
                    text = "Dalai Lama (UK: /ˈdælaɪ ˈlɑːmə/, US: /ˈdɑːlaɪ ˈlɑːmə/;[1][2] Tibetan:" +
                            " ཏཱ་ལའི་བླ་མ་, Wylie: Tā la'i bla ma [táːlɛː láma]) is a title given by the" +
                            " Tibetan people to the foremost spiritual leader of the Gelug or Yellow" +
                            " Hat school of Tibetan Buddhism, the newest of the classical schools of" +
                            " Tibetan Buddhism.[3] The 14th and current Dalai Lama is Tenzin Gyatso," +
                            " who lives as a refugee in India."
                ))
                dao.addNote(NoteEntity(
                    title = "Bill Burr",
                    text = "William Frederick Burr (born June 10, 1968) is an American stand-up comedian," +
                            " actor and podcaster. He created and stars in the Netflix animated sitcom" +
                            " F Is for Family (2015–present), played Patrick Kuby in the AMC crime drama" +
                            " series Breaking Bad (2008–2013), and Migs Mayfeld in the Star Wars television" +
                            " series The Mandalorian (2019–present). He co-founded the All Things Comedy network" +
                            " and has hosted the twice-weekly comedy podcast, titled Monday Morning Podcast," +
                            " since May 2007. His first nomination for a Grammy came in 2021 for his special" +
                            " Paper Tiger."
                ))
            }*/
        }
    }
}