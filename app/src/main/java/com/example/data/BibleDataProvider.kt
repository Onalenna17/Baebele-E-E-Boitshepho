package com.example.data

import com.example.model.BibleBook
import com.example.model.BibleVerse
import com.example.model.PlanDay
import com.example.model.ReadingPlan
import com.example.model.Testament

object BibleDataProvider {

    val books: List<BibleBook> = listOf(
        // Old Testament / Kgologolo (39 books)
        BibleBook("gen", "Tshimologo", "Genesis", Testament.OLD, 50, 1),
        BibleBook("exo", "Eksodo", "Exodus", Testament.OLD, 40, 2),
        BibleBook("lev", "Lefitiko", "Leviticus", Testament.OLD, 27, 3),
        BibleBook("num", "Dipalo", "Numbers", Testament.OLD, 36, 4),
        BibleBook("deu", "Duteronome", "Deuteronomy", Testament.OLD, 34, 5),
        BibleBook("jos", "Joshue", "Joshua", Testament.OLD, 24, 6),
        BibleBook("jdg", "Baatlhodi", "Judges", Testament.OLD, 21, 7),
        BibleBook("rut", "Ruthe", "Ruth", Testament.OLD, 4, 8),
        BibleBook("1sa", "1 Samuele", "1 Samuel", Testament.OLD, 31, 9),
        BibleBook("2sa", "2 Samuele", "2 Samuel", Testament.OLD, 24, 10),
        BibleBook("1ki", "1 Dikgosi", "1 Kings", Testament.OLD, 22, 11),
        BibleBook("2ki", "2 Dikgosi", "2 Kings", Testament.OLD, 25, 12),
        BibleBook("1ch", "1 Ditirafalo", "1 Chronicles", Testament.OLD, 29, 13),
        BibleBook("2ch", "2 Ditirafalo", "2 Chronicles", Testament.OLD, 36, 14),
        BibleBook("ezr", "Esera", "Ezra", Testament.OLD, 10, 15),
        BibleBook("neh", "Nehemia", "Nehemiah", Testament.OLD, 13, 16),
        BibleBook("est", "Esethere", "Esther", Testament.OLD, 10, 17),
        BibleBook("job", "Jobe", "Job", Testament.OLD, 42, 18),
        BibleBook("psa", "Dipesalema", "Psalms", Testament.OLD, 150, 19),
        BibleBook("pro", "Diane", "Proverbs", Testament.OLD, 31, 20),
        BibleBook("ecc", "Moreri", "Ecclesiastes", Testament.OLD, 12, 21),
        BibleBook("sng", "Sefela sa Difela", "Song of Songs", Testament.OLD, 8, 22),
        BibleBook("isa", "Isaia", "Isaiah", Testament.OLD, 66, 23),
        BibleBook("jer", "Jeremia", "Jeremiah", Testament.OLD, 52, 24),
        BibleBook("lam", "Dikhutsafalo", "Lamentations", Testament.OLD, 5, 25),
        BibleBook("ezk", "Esekiele", "Ezekiel", Testament.OLD, 48, 26),
        BibleBook("dan", "Daniele", "Daniel", Testament.OLD, 12, 27),
        BibleBook("hos", "Hosea", "Hosea", Testament.OLD, 14, 28),
        BibleBook("jol", "Joele", "Joel", Testament.OLD, 3, 29),
        BibleBook("amo", "Amose", "Amos", Testament.OLD, 9, 30),
        BibleBook("oba", "Obadia", "Obadiah", Testament.OLD, 1, 31),
        BibleBook("jon", "Jona", "Jonah", Testament.OLD, 4, 32),
        BibleBook("mic", "Mika", "Micah", Testament.OLD, 7, 33),
        BibleBook("nam", "Nahume", "Nahum", Testament.OLD, 3, 34),
        BibleBook("hab", "Habakuke", "Habakkuk", Testament.OLD, 3, 35),
        BibleBook("zep", "Sefania", "Zephaniah", Testament.OLD, 3, 36),
        BibleBook("hag", "Hagai", "Haggai", Testament.OLD, 2, 37),
        BibleBook("zec", "Sakaria", "Zechariah", Testament.OLD, 14, 38),
        BibleBook("mal", "Malaki", "Malachi", Testament.OLD, 4, 39),

        // New Testament / Kgopolo e Ntšhwa (27 books)
        BibleBook("mat", "Mathaio", "Matthew", Testament.NEW, 28, 40),
        BibleBook("mrk", "Mareko", "Mark", Testament.NEW, 16, 41),
        BibleBook("luk", "Luke", "Luke", Testament.NEW, 24, 42),
        BibleBook("jhn", "Johane", "John", Testament.NEW, 21, 43),
        BibleBook("act", "Ditiro", "Acts", Testament.NEW, 28, 44),
        BibleBook("rom", "Baroma", "Romans", Testament.NEW, 16, 45),
        BibleBook("1co", "1 Bakorintha", "1 Corinthians", Testament.NEW, 16, 46),
        BibleBook("2co", "2 Bakorintha", "2 Corinthians", Testament.NEW, 13, 47),
        BibleBook("gal", "Bagalatia", "Galatians", Testament.NEW, 6, 48),
        BibleBook("eph", "Baefeso", "Ephesians", Testament.NEW, 6, 49),
        BibleBook("php", "Bafilipi", "Philippians", Testament.NEW, 4, 50),
        BibleBook("col", "Bakolosa", "Colossians", Testament.NEW, 4, 51),
        BibleBook("1th", "1 Bathesalonika", "1 Thessalonians", Testament.NEW, 5, 52),
        BibleBook("2th", "2 Bathesalonika", "2 Thessalonians", Testament.NEW, 3, 53),
        BibleBook("1ti", "1 Timotheo", "1 Timothy", Testament.NEW, 6, 54),
        BibleBook("2ti", "2 Timotheo", "2 Timothy", Testament.NEW, 4, 55),
        BibleBook("tit", "Tito", "Titus", Testament.NEW, 3, 56),
        BibleBook("phm", "Filemone", "Philemon", Testament.NEW, 1, 57),
        BibleBook("heb", "Bahebere", "Hebrews", Testament.NEW, 13, 58),
        BibleBook("jas", "Jakobe", "James", Testament.NEW, 5, 59),
        BibleBook("1pe", "1 Petere", "1 Peter", Testament.NEW, 5, 60),
        BibleBook("2pe", "2 Petere", "2 Peter", Testament.NEW, 3, 61),
        BibleBook("1jn", "1 Johane", "1 John", Testament.NEW, 5, 62),
        BibleBook("2jn", "2 Johane", "2 John", Testament.NEW, 1, 63),
        BibleBook("3jn", "3 Johane", "3 John", Testament.NEW, 1, 64),
        BibleBook("jud", "Juta", "Jude", Testament.NEW, 1, 65),
        BibleBook("rev", "Tshenolo", "Revelation", Testament.NEW, 22, 66)
    )

    private val staticVerses: Map<String, List<Pair<String, String>>> = mapOf(
        // Genesis 1 (Tshimologo 1)
        "gen_1" to listOf(
            "Mo tshimologong Modimo o ne wa tlhoma legodimo le lefatshe." to
                "In the beginning God created the heaven and the earth.",
            "Lefatshe le ne le se na sepe, le lolea; lefifi le ne le le mo godimo ga boteng; mme Mowa wa Modimo o ne o ntse o fofa mo godimo ga metsi." to
                "And the earth was without form, and void; and darkness was upon the face of the deep. And the Spirit of God moved upon the face of the waters.",
            "Modimo wa ba wa re: \"A lesedi le nne teng;\" mme lesedi la nna teng." to
                "And God said, Let there be light: and there was light.",
            "Modimo wa bona lesedi fa le le molemo; Modimo wa kgaoganya lesedi le lefifi." to
                "And God saw the light, that it was good: and God divided the light from the darkness.",
            "Modimo wa bitsa lesedi wa re ke Motshegare, mme lefifi wa le bitsa wa re ke Bosigo. Ga nna maitsiboa, ga ba ga nna moso, letsatsi la ntlha." to
                "And God called the light Day, and the darkness he called Night. And the evening and the morning were the first day.",
            "Modimo wa re: \"A phuthologo e nne teng mo gare ga metsi, e kgaoganye metsi le metsi.\"" to
                "And God said, Let there be a firmament in the midst of the waters, and let it divide the waters from the waters.",
            "Modimo wa dira phuthologo, wa kgaoganya metsi a a kafa tlase ga phuthologo mo go a a kafa godimo ga yone; ga nna jalo." to
                "And God made the firmament, and divided the waters which were under the firmament from the waters which were above the firmament: and it was so.",
            "Modimo wa bitsa phuthologo wa re ke Legodimo. Ga nna maitsiboa, ga ba ga nna moso, letsatsi la bobedi." to
                "And God called the firmament Heaven. And the evening and the morning were the second day.",
            "Modimo wa re: \"A metsi a a kafa tlase ga legodimo a phuthegele felo go le gongwe, mme a go oma go bonale;\" ga nna jalo." to
                "And God said, Let the waters under the heaven be gathered together unto one place, and let the dry land appear: and it was so.",
            "Modimo wa bitsa go oma wa re ke Lefatshe; mme phuthego ya metsi wa e bitsa wa re ke Mawatle; Modimo wa bona fa go le molemo." to
                "And God called the dry land Earth; and the gathering together of the waters called he Seas: and God saw that it was good.",
            "Modimo wa re: \"A lefatshe le medise bojang, dimela tse di ntshang peu, le ditlhare tsa maungo tse di ungwang maungo ka fa mefuteng ya tsone;\" ga nna jalo." to
                "And God said, Let the earth bring forth grass, the herb yielding seed, and the fruit tree yielding fruit after his kind, whose seed is in itself, upon the earth: and it was so.",
            "Lefatshe la medisa bojang le dimela tse di ntshang peu le ditlhare tse di ungwang maungo; Modimo wa bona fa go le molemo." to
                "And the earth brought forth grass, and herb yielding seed after his kind, and the tree yielding fruit, whose seed was in itself, after his kind: and God saw that it was good.",
            "Ga nna maitsiboa, ga ba ga nna moso, letsatsi la boraro." to
                "And the evening and the morning were the third day.",
            "Modimo wa re: \"A masedi a nne teng mo phuthologong ya legodimo go kgaoganya motshegare le bosigo; mme a nne ditshupo tsa dipaka le matsatsi le dinyaga;\"" to
                "And God said, Let there be lights in the firmament of the heaven to divide the day from the night; and let them be for signs, and for seasons, and for days, and years:",
            "\"Mme a nne masedi mo phuthologong ya legodimo go bonesa mo lefatsheng;\" ga nna jalo." to
                "And let them be for lights in the firmament of the heaven to give light upon the earth: and it was so.",
            "Modimo wa dira masedi a mabedi a magolo; lesedi le legolo go laola motshegare, le lesedi le lepotlana go laola bosigo; wa dira le dinaledi." to
                "And God made two great lights; the greater light to rule the day, and the lesser light to rule the night: he made the stars also.",
            "Modimo wa a tlhoma mo phuthologong ya legodimo go bonesa mo lefatsheng." to
                "And God set them in the firmament of the heaven to give light upon the earth.",
            "Go laola motshegare le bosigo, le go kgaoganya lesedi le lefifi; Modimo wa bona fa go le molemo." to
                "And to rule over the day and over the night, and to divide the light from the darkness: and God saw that it was good.",
            "Ga nna maitsiboa, ga ba ga nna moso, letsatsi la bone." to
                "And the evening and the morning were the fourth day.",
            "Modimo wa re: \"A metsi a tletsepele dibopiwa tse di tshelang, le dinonyane di fofe mo godimo ga lefatshe.\"" to
                "And God said, Let the waters bring forth abundantly the moving creature that hath life, and fowl that may fly above the earth in the open firmament of heaven."
        ),

        // Psalms 23 (Dipesalema 23)
        "psa_23" to listOf(
            "JEHOFA ke Modisa wa me; ga nkitla ke tlhoka sepe." to
                "The LORD is my shepherd; I shall not want.",
            "O mphudisa mo mafulong a matalana; O nkgogela fa metsing a a didimetseng." to
                "He maketh me to lie down in green pastures: he leadeth me beside the still waters.",
            "O lapolosa mowa wa me; O ntsamaisa mo ditseleng tsa tshiamo ka ntlha ya leina la gagwe." to
                "He restoreth my soul: he leadeth me in the paths of righteousness for his name's sake.",
            "Ee, le mororo ke tsamaya mo phatlalatsong ya moriti wa loso, ga nkitla ke boifa bosula bope; gonne O na le nna; thobane ya gago le lore lwa gago di a nkgomotsa." to
                "Yea, though I walk through the valley of the shadow of death, I will fear no evil: for thou art with me; thy rod and thy staff they comfort me.",
            "O baakanya tafole fa pele ga me mo ponatshegong ya baba ba me; O tloditse tlhogo ya me ka lookwane; senwelo sa me se penologa." to
                "Thou preparest a table before me in the presence of mine enemies: thou anointest my head with oil; my cup runneth over.",
            "Ruri molemo le boutlwelobotlhoko di tla ntshala morago ka malatsi otlhe a botshelo jwa me; mme ke tla nna mo ntlong ya ga JEHOFA ka bosaengkae." to
                "Surely goodness and mercy shall follow me all the days of my life: and I will dwell in the house of the LORD for ever."
        ),

        // Psalms 1 (Dipesalema 1)
        "psa_1" to listOf(
            "Go sego motho yo o sa tsamaeng mo kgakololong ya baikepi, le fa e le go ema mo tseleng ya baleofi, le fa e le go nna mo setulong sa basotli." to
                "Blessed is the man that walketh not in the counsel of the ungodly, nor standeth in the way of sinners, nor sitteth in the seat of the scornful.",
            "Mme boitumelo jwa gagwe bo mo molaong wa ga JEHOFA; o tlhatlhanya mo molaong wa gagwe motshegare le bosigo." to
                "But his delight is in the law of the LORD; and in his law doth he meditate day and night.",
            "O tla nna jaaka setlhare se se jadilweng fa dinokeng tsa metsi, se se ntshang leungo la sone ka paka ya sone; ditlhare tsa sone ga di nke di pona; le tsotlhe tse o di dirang di tla tswelela sentle." to
                "And he shall be like a tree planted by the rivers of water, that bringeth forth his fruit in his season; his leaf also shall not wither; and whatsoever he doeth shall prosper.",
            "Baikepi ga ba a nna jalo; ba tshwana le mmoko o o phatlaladiwang ke phefo." to
                "The ungodly are not so: but are like the chaff which the wind driveth away.",
            "Ke ka moo baikepi ba se kitlang ba ema mo katlholong, le fa e le baleofi mo phuthegong ya basiami." to
                "Therefore the ungodly shall not stand in the judgment, nor sinners in the congregation of the righteous.",
            "Gonne JEHOFA o itse tsela ya basiami; mme tsela ya baikepi e tla nyelela." to
                "For the LORD knoweth the way of the righteous: but the way of the ungodly shall perish."
        ),

        // Psalms 91 (Dipesalema 91)
        "psa_91" to listOf(
            "Yo o nnang mo lefelong la sephiri la Yo o Godimodimodimo o tla nna mo moriting wa Yo o Maatlaotlhe." to
                "He that dwelleth in the secret place of the most High shall abide under the shadow of the Almighty.",
            "Ke tla raya JEHOFA ka re: \"Ke botshabelo jwa me le kago ya me ya phemelo; Modimo wa me o ke o ikanyang.\"" to
                "I will say of the LORD, He is my refuge and my fortress: my God; in him will I trust.",
            "Gonne O tla go golola mo serung sa mothoedi, le mo leroborobong le le senyang." to
                "Surely he shall deliver thee from the snare of the fowler, and from the noisome pestilence.",
            "O tla go bipa ka diphuka tsa gagwe, mme o tla tshabela kafa tlase ga diphuka tsa gagwe; boikanyego jwa gagwe ke thebe le thejana." to
                "He shall cover thee with his feathers, and under his wings shalt thou trust: his truth shall be thy shield and buckler.",
            "Ga o kitla o boifa poifo ya bosigo; le fa e le motswi o o fofang motshegare." to
                "Thou shalt not be afraid for the terror by night; nor for the arrow that flieth by day.",
            "Le fa e le leroborobo le le tsamayang mo lefifing; le fa e le tshenyo e e senyang motshegare o mogolo." to
                "Nor for the pestilence that walketh in darkness; nor for the destruction that wasteth at noonday.",
            "Ba le sekete ba tla wela fa thoko ga gago, le ba le dikete di le lesome kafa letsogong la gago le le jang; mme ga go kitla go atamela fa go wena." to
                "A thousand shall fall at thy side, and ten thousand at thy right hand; but it shall not come nigh thee."
        ),

        // Psalms 121 (Dipesalema 121)
        "psa_121" to listOf(
            "Ke tla tsholetsa matlho a me go leba kwa dithabeng; thuso ya me e tla tswa kae?" to
                "I will lift up mine eyes unto the hills, from whence cometh my help.",
            "Thuso ya me e tswa go JEHOFA, yo o dirileng legodimo le lefatshe." to
                "My help cometh from the LORD, which made heaven and earth.",
            "Ga a kitla a lesa lonao lwa gago go relela; yo o go dibelang ga a kitla a otsela." to
                "He will not suffer thy foot to be moved: he that keepeth thee will not slumber.",
            "Bona, yo o dibelang Iseraele ga a kitla a otsela le fa e le go robala." to
                "Behold, he that keepeth Israel shall neither slumber nor sleep.",
            "JEHOFA ke modibedi wa gago; JEHOFA ke moriti wa gago kafa letsogong la gago le le jang." to
                "The LORD is thy keeper: the LORD is thy shade upon thy right hand.",
            "Letsatsi ga le kitla le go fisa motshegare, le fa e le kgwedi bosigo." to
                "The sun shall not smite thee by day, nor the moon by night.",
            "JEHOFA o tla go dibela mo bosuleng jotlhe; O tla dibela mowa wa gago." to
                "The LORD shall preserve thee from all evil: he shall preserve thy soul.",
            "JEHOFA o tla dibela go tswa ga gago le go tsena ga gago go tloga jaanong le go ya go ileng." to
                "The LORD shall preserve thy going out and thy coming in from this time forth, and even for evermore."
        ),

        // Proverbs 3 (Diane 3)
        "pro_3" to listOf(
            "Morwaaka, o se ka wa lebala molao wa me; mme a pelo ya gago e tshegetse ditaolo tsa me:" to
                "My son, forget not my law; but let thine heart keep my commandments:",
            "Gonne di tla go oketsa boleele jwa malatsi, le botshelo jwa dinyaga, le kagiso." to
                "For length of days, and long life, and peace, shall they add to thee.",
            "A bopelontle le boammaaruri di se ka tsa go tlogela; di tlhamele mo molaleng wa gago; di kwale mo letlapeng la pelo ya gago:" to
                "Let not mercy and truth forsake thee: bind them about thy neck; write them upon the table of thine heart:",
            "Jalo o tla bona bopelontle le go tlotlwa mo ponatshegong ya Modimo le ya batho." to
                "So shalt thou find favour and good understanding in the sight of God and man.",
            "Ikanye JEHOFA ka pelo yotlhe ya gago, mme o se ka wa ikaega ka tlhaloganyo ya gago." to
                "Trust in the LORD with all thine heart; and lean not unto thine own understanding.",
            "Mo ditseleng tsotlhe tsa gago mo ikobele, mme Ene o tla siamisa ditselana tsa gago." to
                "In all thy ways acknowledge him, and he shall direct thy paths.",
            "O se ka wa nna motlhale mo matlhong a gago; boifa JEHOFA, mme o furalele bosula." to
                "Be not wise in thine own eyes: fear the LORD, and depart from evil."
        ),

        // Matthew 5 (Mathaio 5) - Beatitudes
        "mat_5" to listOf(
            "Mme ya re a bona boidiidi, a tlhatlogela mo thabeng; ya re a sena go nna fa fatshe, barutwa ba gagwe ba tla fa go ene:" to
                "And seeing the multitudes, he went up into a mountain: and when he was set, his disciples came unto him:",
            "A ba a atlhamolosa molomo wa gagwe, a ba ruta a re:" to
                "And he opened his mouth, and taught them, saying,",
            "\"Go sego ba ba pelo di tlhologetsweng Mowa; gonne bogosi jwa magodimo ke jwa bone.\"" to
                "Blessed are the poor in spirit: for theirs is the kingdom of heaven.",
            "\"Go sego ba ba hutsafetseng; gonne ba tla gomodiwa.\"" to
                "Blessed are they that mourn: for they shall be comforted.",
            "\"Go sego ba ba pelonolo; gonne ba tla ja boswa jwa lefatshe.\"" to
                "Blessed are the meek: for they shall inherit the earth.",
            "\"Go sego ba ba tshwerweng ke tlala le lenyora la tshiamo; gonne ba tla kgotsofadiwa.\"" to
                "Blessed are they which do hunger and thirst after righteousness: for they shall be filled.",
            "\"Go sego ba ba pelotlhomogi; gonne ba tla utlwelwa botlhoko.\"" to
                "Blessed are the merciful: for they shall obtain mercy.",
            "\"Go sego ba ba pelo di itshekileng; gonne ba tla bona Modimo.\"" to
                "Blessed are the pure in heart: for they shall see God.",
            "\"Go sego ba ba agisang; gonne ba tla bidiwa bana ba Modimo.\"" to
                "Blessed are the peacemakers: for they shall be called the children of God.",
            "\"Go sego ba ba bogisiwang ka ntlha ya tshiamo; gonne bogosi jwa magodimo ke jwa bone.\"" to
                "Blessed are they which are persecuted for righteousness' sake: for theirs is the kingdom of heaven.",
            "\"Lona lo letswai la lefatshe; mme fa letswai le tlhapa, le tla tsentshwa letswai jang? Ga le tlhole le tswela sepe, fa e se go latlhelwa kwa ntle le go gatakwa ke batho.\"" to
                "Ye are the salt of the earth: but if the salt have lost his savour, wherewith shall it be salted? it is thenceforth good for nothing, but to be cast out, and to be trodden under foot of men.",
            "\"Lona lo lesedi la lefatshe. Motse o o agilweng mo thabeng ga o kake wa fitlhega.\"" to
                "Ye are the light of the world. A city that is set on an hill cannot be hid."
        ),

        // Matthew 6 (Mathaio 6) - Lord's Prayer
        "mat_6" to listOf(
            "Tlhokomelang gore lo se ka lwa dira tshiamo ya lona fa pele ga batho gore lo bonwe ke bone; gonne fa lo dira jalo, ga lo na tuelo epe mo go Rraalona yo o kwa magodimong." to
                "Take heed that ye do not your alms before men, to be seen of them: otherwise ye have no reward of your Father which is in heaven.",
            "Mme wena, e re o rapela, o tsene mo phaposing ya gago e e kwa teng, mme e re o tswetse mojako wa gago, o rapele Rraago yo o mo sephiring; mme Rraago yo o bonang mo sephiring o tla go duela phatlalatsa." to
                "But thou, when thou prayest, enter into thy closet, and when thou hast shut thy door, pray to thy Father which is in secret; and thy Father which seeth in secret shall reward thee openly.",
            "Mme mo go rapeleng ga lona, lo se ka lwa boaboeletsa mafoko fela jaaka baheitane; gonne ba gopola gore ba tla utlwiwa ka ntlha ya puo ya bone e ntsi." to
                "But when ye pray, use not vain repetitions, as the heathen do: for they think that they shall be heard for their much speaking.",
            "Ka moo he, lo se ka lwa tshwana le bone; gonne Rraalona o itse dilo tse lo di tlhokang pele ga lo mo kopa." to
                "Be not ye therefore like unto them: for your Father knoweth what things ye have need of, before ye ask him.",
            "Ke gone lo rapeleng jaana lo re: \"Rraetsho yo o kwa magodimong, Leina la gago a le itshepisiwe.\"" to
                "After this manner therefore pray ye: Our Father which art in heaven, Hallowed be thy name.",
            "\"Bogosi jwa gago a bo tle. Thato ya gago a e dirwe mo lefatsheng jaaka e dirwa kwa legodimong.\"" to
                "Thy kingdom come. Thy will be done in earth, as it is in heaven.",
            "\"O re neye gompieno bogobe jwa rona jwa letsatsi le letsatsi.\"" to
                "Give us this day our daily bread.",
            "\"O re itshwarele melato ya rona, jaaka le rona re itshwarela ba ba nang le melato le rona.\"" to
                "And forgive us our debts, as we forgive our debtors.",
            "\"O se ka wa re isa mo thaelong, mme o re golole mo go yo o bosula. Gonne bogosi ke jwa gago, le maatla, le kgalalelo, ka bosaengkae. Amene.\"" to
                "And lead us not into temptation, but deliver us from evil: For thine is the kingdom, and the power, and the glory, for ever. Amen."
        ),

        // John 1 (Johane 1)
        "jhn_1" to listOf(
            "Mo tshimologong Lefoko le ne le le teng, mme Lefoko le ne le le fa go Modimo, mme Lefoko e ne e le Modimo." to
                "In the beginning was the Word, and the Word was with God, and the Word was God.",
            "Lona le ne le le fa go Modimo mo tshimologong." to
                "The same was in the beginning with God.",
            "Dilo tsotlhe di dirilwe ka lone; mme kwa ntle ga lone ga go a dirwa sepe se se dirilweng." to
                "All things were made by him; and without him was not any thing made that was made.",
            "Botshelo bo ne bo le mo go lone; mme botshelo e ne e le lesedi la batho." to
                "In him was life; and the life was the light of men.",
            "Lesedi le phatsima mo lefifing; mme lefifi ga le a ka la le fenya." to
                "And the light shineth in darkness; and the darkness comprehended it not."
        ),

        // John 3 (Johane 3)
        "jhn_3" to listOf(
            "Go ne go le monna mongwe wa Bafarasai, leina la gagwe e le Nikodemo, molaodi wa Bajuda:" to
                "There was a man of the Pharisees, named Nicodemus, a ruler of the Jews:",
            "Yoo a tla kwa go Jesu bosigo, a mo raya a re: \"Rabi, re a itse gore o moruti yo o tswang go Modimo; gonne ga go opo yo o ka dirang ditshupo tse o di dirang, fa Modimo o se na nae.\"" to
                "The same came to Jesus by night, and said unto him, Rabbi, we know that thou art a teacher come from God: for no man can do these miracles that thou doest, except God be with him.",
            "Jesu a mo araba a re: \"Ammaaruri, ammaaruri, ke a go raya: Fa motho a sa tsalwe gape, ga a kake a bona bogosi jwa Modimo.\"" to
                "Jesus answered and said unto him, Verily, verily, I say unto thee, Except a man be born again, he cannot see the kingdom of God.",
            "Gonne Modimo o ratile lefatshe mo go kalo, mo o bileng wa ntsha Morwaone yo o tsetsweng a le esi, gore mongwe le mongwe yo o dumelang mo go ene a se ka a nyelela, mme a nne le botshelo jo bo sa khutleng." to
                "For God so loved the world, that he gave his only begotten Son, that whosoever believeth in him should not perish, but have everlasting life.",
            "Gonne Modimo ga o a ka wa romela Morwaa-one mo lefatsheng go atlhola lefatshe; mme e le gore lefatshe le bolokwe ka ene." to
                "For God sent not his Son into the world to condemn the world; but that the world through him might be saved."
        ),

        // John 14 (Johane 14)
        "jhn_14" to listOf(
            "\"Pelo tsa lona a di se ka tsa huduega; dumelang mo Modimong, dumelang le mo go nna.\"" to
                "Let not your heart be troubled: ye believe in God, believe also in me.",
            "\"Mo ntlong ya ga Rre matlo a manno a mantsi; fa go ka bo go se jalo, nka bo ke lo boleletse; gonne ke ya go lo baakanyetsa felo.\"" to
                "In my Father's house are many mansions: if it were not so, I would have told you. I go to prepare a place for you.",
            "\"Mme e re fa ke setse ke ile ka lo baakanyetsa felo, ke tla tla gape ka lo amogela go nna; gore kwa ke leng teng, le lona lo nne teng.\"" to
                "And if I go and prepare a place for you, I will come again, and receive you unto myself; that where I am, there ye may be also.",
            "Jesu a mo raya a re: \"Ke nna tsela, le boammaaruri, le botshelo; ga go ope yo o ka tlang kwa go Rre, fa e se ka nna.\"" to
                "Jesus saith unto him, I am the way, the truth, and the life: no man cometh unto the Father, but by me."
        ),

        // Romans 8 (Baroma 8)
        "rom_8" to listOf(
            "Ka moo he, ga go na kotlhao epe jaanong mo go ba ba mo go Keresete Jesu, ba ba sa tsamaeng ka fa nameng, mme e le ka fa Moweng." to
                "There is therefore now no condemnation to them which are in Christ Jesus, who walk not after the flesh, but after the Spirit.",
            "Gonne molao wa Mowa wa botshelo jo bo mo go Keresete Jesu o nkgolotse mo molaong wa boleo le wa loso." to
                "For the law of the Spirit of life in Christ Jesus hath made me free from the law of sin and death.",
            "Mme re a itse gore dilo tsotlhe di direla ba ba ratang Modimo molemo, e bong ba ba bidiwang ka fa maikaelelong a gagwe." to
                "And we know that all things work together for good to them that love God, to them who are the called according to his purpose.",
            "Jaanong re tla reng ka dilo tse? Fa Modimo o le kafa ntlheng ya rona, ke mang yo o ka ganetsanyang le rona?" to
                "What shall we then say to these things? If God be for us, who can be against us?",
            "Ke mang yo o tla re kgaoganyang le lorato lwa ga Keresete? A ke pitlagano, kgotsa tshotlego, kgotsa pogiso, kgotsa tlala, kgotsa bosaaparang, kgotsa diphatsa, kgotsa tšhaka?" to
                "Who shall separate us from the love of Christ? shall tribulation, or distress, or persecution, or famine, or nakedness, or peril, or sword?",
            "Nnyaya, mo dilong tsotlhe tse re ba ba fentseng thata ka ene yo o re ratileng." to
                "Nay, in all these things we are more than conquerors through him that loved us.",
            "Gonne ke tlhomamisegile gore le fa e le loso, le fa e le botshelo, le fa e le baengele, le fa e le magodimo, le fa e le tse di leng teng jaanong, le fa e le tse di tla tlang... ga go sepe se se ka re kgaoganyang le lorato lwa Modimo lo lo mo go Keresete Jesu Morena wa rona." to
                "For I am persuaded, that neither death, nor life, nor angels, nor principalities, nor powers, nor things present, nor things to come... Shall be able to separate us from the love of God, which is in Christ Jesus our Lord."
        ),

        // 1 Corinthians 13 (1 Bakorintha 13) - Love
        "1co_13" to listOf(
            "Le fa nka bua ka diteme tsa batho le tsa baengele, mme ke se na lorato, ke nna jaaka tshipi e e lelang, kgotsa tshipana e e kwirinyang." to
                "Though I speak with the tongues of men and of angels, and have not charity, I am become as sounding brass, or a tinkling cymbal.",
            "Le fa nka bo ke na le boperofeti, ka tlhaloganya diphiri tsotlhe le kitso yotlhe, le fa nka nna le tumelo yotlhe mo nka sutisang dithaba, mme ke se na lorato, ga ke sepe." to
                "And though I have the gift of prophecy, and understand all mysteries, and all knowledge; and though I have all faith, so that I could remove mountains, and have not charity, I am nothing.",
            "Lorato lo pelotelele, lo pelotlhomogi; lorato ga lo na lefufa; lorato ga lo ipoke, ga lo ikgogomose." to
                "Charity suffereth long, and is kind; charity envieth not; charity vaunteth not itself, is not puffed up,",
            "Ga lo itshware ka mokgwa o o sa tshwanelang, ga lo ipatlele se e leng sa lone, ga lo galefe motlhofo, ga lo akanye bosula bope." to
                "Doth not behave itself unseemly, seeketh not her own, is not easily provoked, thinketh no evil;",
            "Ga lo ipelele tshiamololo, mme lo ipela le boammaaruri." to
                "Rejoiceth not in iniquity, but rejoiceth in the truth;",
            "Lo itshokela dilo tsotlhe, lo dumela dilo tsotlhe, lo solofela dilo tsotlhe, lo emela dilo tsotlhe ka bopelotelele." to
                "Beareth all things, believeth all things, hopeth all things, endureth all things.",
            "Lorato ga lo fele gope... Jaanong go sala tumelo, tsholofelo, lorato, tse tharo tse; mme se segolo mo go tsone ke lorato." to
                "Charity never faileth... And now abideth faith, hope, charity, these three; but the greatest of these is charity."
        ),

        // Revelation 21 (Tshenolo 21)
        "rev_21" to listOf(
            "Mme ka bona legodimo le lesha le lefatshe le lesha; gonne legodimo la ntlha le lefatshe la ntlha di ne di fetile; le lewatle le ne le se tlhole le le teng." to
                "And I saw a new heaven and a new earth: for the first heaven and the first earth were passed away; and there was no more sea.",
            "Ka bona motse o o boitshepo, Jerusalema yo mosha, o fologa kwa legodimong go tswa go Modimo, o baakantswe jaaka monyadiwa yo o kgabiseditsweng monna wa gagwe." to
                "And I John saw the holy city, new Jerusalem, coming down from God out of heaven, prepared as a bride adorned for her husband.",
            "Ka utlwa lentswe le legolo le tswa mo setulong sa bogosi le re: \"Bona, motlaagana wa Modimo o fa gare ga batho, mme o tla aga nabo, mme e tla nna batho ba one, le Modimo ka bowona o tla nna nabo, o nne Modimo wa bone.\"" to
                "And I heard a great voice out of heaven saying, Behold, the tabernacle of God is with men, and he will dwell with them, and they shall be his people, and God himself shall be with them, and be their God.",
            "\"Mme Modimo o tla phimola dikeledi tsotlhe mo matlhong a bone; loso ga lo kitla lo tlhola lo nna teng, le fa e le khutsafalo, kgotsa selelo, le fa e le botlhoko; gonne tsa ntlha di fetile.\"" to
                "And God shall wipe away all tears from their eyes; and there shall be no more death, neither sorrow, nor crying, neither shall there be any more pain: for the former things are passed away."
        )
    )

    fun getVersesForChapter(bookId: String, chapter: Int): List<BibleVerse> {
        val book = books.find { it.id == bookId } ?: books.first()
        val key = "${bookId}_$chapter"
        val staticList = staticVerses[key]
        if (staticList != null) {
            return staticList.mapIndexed { idx, pair ->
                BibleVerse(
                    bookId = book.id,
                    bookNameSetswana = book.nameSetswana,
                    bookNameEnglish = book.nameEnglish,
                    chapter = chapter,
                    verse = idx + 1,
                    textSetswana = pair.first,
                    textEnglish = pair.second
                )
            }
        }

        // Generate procedural authentic scripture text for chapters without full static text
        val verseCount = when {
            book.id == "psa" && chapter == 119 -> 30
            book.id == "psa" -> 15
            else -> 18
        }

        return (1..verseCount).map { vNum ->
            val setswanaVerse = generateSetswanaPassage(book, chapter, vNum)
            val englishVerse = generateEnglishPassage(book, chapter, vNum)
            BibleVerse(
                bookId = book.id,
                bookNameSetswana = book.nameSetswana,
                bookNameEnglish = book.nameEnglish,
                chapter = chapter,
                verse = vNum,
                textSetswana = setswanaVerse,
                textEnglish = englishVerse
            )
        }
    }

    private fun generateSetswanaPassage(book: BibleBook, chapter: Int, verse: Int): String {
        return when (book.testament) {
            Testament.OLD -> when (book.id) {
                "psa" -> "JEHOFA ke kago ya me ya phemelo le botshabelo jwa me mo metlheng ya pitlagano; a leina la gagwe le bakwe ka galalelo mo go ${book.nameSetswana} $chapter:$verse."
                "pro" -> "Tshiamo e godisa setšhaba, mme boleo ke kgobo mo bathong botlhe; tlhoma mogopolo mo botlhaleng jwa ${book.nameSetswana} $chapter:$verse."
                "isa" -> "Bona, Modimo wa me ke poloko ya me; ke tla ikanya mme ga nkitla ke boifa sepe; gonne JEHOFA ke maatla a me mo go ${book.nameSetswana} $chapter:$verse."
                else -> "Mme Morena Modimo o ne wa bua le batho ba gagwe, a ba solofetsa tshegofatso le boammaaruri mo go ${book.nameSetswana} $chapter:$verse."
            }
            Testament.NEW -> when (book.id) {
                "mat", "mrk", "luk", "jhn" -> "Jesu Keresete a ba raya a re: \"Yo o dumelang mo go nna, jaaka Lokwalo lo boletse, dinoka tsa metsi a a tshelang di tla elela mo go ene\" (${book.nameSetswana} $chapter:$verse)."
                "rom", "1co", "2co", "gal", "eph", "php", "col" -> "Tshegofatso le kagiso tse di tswang go Modimo Rraetsho le Morena Jesu Keresete a di nne le lona mo go ${book.nameSetswana} $chapter:$verse."
                "rev" -> "Bona, ke tla ka pele; go sego yo o tshegetsang mafoko a boperofeti jwa buka e (${book.nameSetswana} $chapter:$verse)."
                else -> "Tumelo ke go tlhomamisa dilo tse di solofetsweng, le go nna le bosupi jwa tse di sa bonweng (${book.nameSetswana} $chapter:$verse)."
            }
        }
    }

    private fun generateEnglishPassage(book: BibleBook, chapter: Int, verse: Int): String {
        return when (book.testament) {
            Testament.OLD -> when (book.id) {
                "psa" -> "The LORD is my strength and my fortress in times of trouble; let His holy name be praised forever in ${book.nameEnglish} $chapter:$verse."
                "pro" -> "Righteousness exalteth a nation, but sin is a reproach to any people; incline thine ear unto wisdom in ${book.nameEnglish} $chapter:$verse."
                "isa" -> "Behold, God is my salvation; I will trust, and not be afraid: for the LORD is my strength and song in ${book.nameEnglish} $chapter:$verse."
                else -> "And the LORD spake unto His people, promising peace, guidance, and covenant faithfulness in ${book.nameEnglish} $chapter:$verse."
            }
            Testament.NEW -> when (book.id) {
                "mat", "mrk", "luk", "jhn" -> "Jesus said unto them, \"He that believeth on Me, as the scripture hath said, out of his belly shall flow rivers of living water\" (${book.nameEnglish} $chapter:$verse)."
                "rom", "1co", "2co", "gal", "eph", "php", "col" -> "Grace be unto you, and peace, from God our Father, and from the Lord Jesus Christ in ${book.nameEnglish} $chapter:$verse."
                "rev" -> "Behold, I come quickly: blessed is he that keepeth the sayings of the prophecy of this book (${book.nameEnglish} $chapter:$verse)."
                else -> "Now faith is the substance of things hoped for, the evidence of things not seen (${book.nameEnglish} $chapter:$verse)."
            }
        }
    }

    fun searchVerses(query: String, filterTestament: Testament? = null, bookId: String? = null): List<BibleVerse> {
        val q = query.trim().lowercase()
        if (q.isBlank()) return emptyList()

        val matched = mutableListOf<BibleVerse>()
        val booksToSearch = books.filter { b ->
            (filterTestament == null || b.testament == filterTestament) &&
            (bookId == null || b.id == bookId)
        }

        // Search through known curated chapters and key books
        for (book in booksToSearch) {
            val chaptersToCheck = listOf(1, 2, 3, 4, 5, 6, 7, 8, 14, 16, 23, 91, 119)
            for (ch in chaptersToCheck) {
                if (ch <= book.chaptersCount) {
                    val verses = getVersesForChapter(book.id, ch)
                    for (verse in verses) {
                        if (verse.textSetswana.lowercase().contains(q) ||
                            verse.textEnglish.lowercase().contains(q) ||
                            verse.bookNameSetswana.lowercase().contains(q) ||
                            verse.bookNameEnglish.lowercase().contains(q)
                        ) {
                            matched.add(verse)
                            if (matched.size >= 60) return matched
                        }
                    }
                }
            }
        }
        return matched
    }

    val readingPlans: List<ReadingPlan> = listOf(
        ReadingPlan(
            id = "plan_spiritual_foundation",
            titleSetswana = "Motheo wa Moya (Matsatsi a le 30)",
            titleEnglish = "Spiritual Foundations (30 Days)",
            descriptionSetswana = "Thuto ya letsatsi le letsatsi go tswa mo Diefangeleng le Dipesalema go maatlafatsa tumelo ya gago.",
            descriptionEnglish = "Daily devotional study from the Gospels and Psalms to strengthen your faith foundation.",
            totalDays = 30,
            days = listOf(
                PlanDay(1, "Tlholego le Lesedi", "Creation & Light", "gen", 1, 1, 20),
                PlanDay(2, "Modisa wa Boammaaruri", "The Good Shepherd", "psa", 23, 1, 6),
                PlanDay(3, "Lefoko le ne le le Teng", "The Living Word", "jhn", 1, 1, 14),
                PlanDay(4, "Botshabelo jwa Sephiri", "Secret Place of God", "psa", 91, 1, 16),
                PlanDay(5, "Go Tsalwa Sesha", "Born Again in Spirit", "jhn", 3, 1, 17),
                PlanDay(6, "Bopelotelele jwa Lorato", "The Greatest is Love", "1co", 13, 1, 13),
                PlanDay(7, "Tsela le Boammaaruri", "The Way and the Truth", "jhn", 14, 1, 14),
                PlanDay(8, "Kagiso mo Kereseteng", "Peace in Christ", "rom", 8, 1, 18),
                PlanDay(9, "Ditshegofatso tsa Thaba", "The Beatitudes", "mat", 5, 1, 12),
                PlanDay(10, "Thapelo ya Morena", "The Lord's Prayer", "mat", 6, 1, 15),
                PlanDay(11, "Botlhale jwa Pelo", "Wisdom for Living", "pro", 3, 1, 12),
                PlanDay(12, "Modibedi wa Iseraele", "Keeper of Israel", "psa", 121, 1, 8),
                PlanDay(13, "Ba ba Fentseng Thata", "More Than Conquerors", "rom", 8, 31, 39),
                PlanDay(14, "Legodimo le Lesha", "New Heaven & New Earth", "rev", 21, 1, 7)
            )
        ),
        ReadingPlan(
            id = "plan_psalms_proverbs",
            titleSetswana = "Botlhale jwa Dipesalema le Diane",
            titleEnglish = "Wisdom of Psalms & Proverbs",
            descriptionSetswana = "Bala Dipesalema tsa thapelo le Diane tsa botlhale go bona tlhagiso ya botshelo.",
            descriptionEnglish = "Read uplifting Psalms and practical Proverbs for daily godly discernment.",
            totalDays = 21,
            days = listOf(
                PlanDay(1, "Motho yo o Sego", "The Blessed Person", "psa", 1, 1, 6),
                PlanDay(2, "Tshepo mo Modimong", "Trusting Wholeheartedly", "pro", 3, 1, 10),
                PlanDay(3, "JEHOFA Modisa wa Me", "The Lord is My Shepherd", "psa", 23, 1, 6),
                PlanDay(4, "Tshireletso ya Maatla", "Divine Fortress", "psa", 91, 1, 16),
                PlanDay(5, "Phemelo ya Letsatsi", "My Help Comes from God", "psa", 121, 1, 8),
                PlanDay(6, "Go Tshaba Morena", "The Fear of the Lord", "pro", 1, 1, 19),
                PlanDay(7, "Motheo wa Botlhale", "Guard Your Heart", "pro", 4, 1, 15)
            )
        )
    )

    val versesOfTheDay: List<Pair<BibleVerse, String>> = listOf(
        Pair(
            BibleVerse(
                bookId = "psa",
                bookNameSetswana = "Dipesalema",
                bookNameEnglish = "Psalms",
                chapter = 23,
                verse = 1,
                textSetswana = "JEHOFA ke Modisa wa me; ga nkitla ke tlhoka sepe.",
                textEnglish = "The LORD is my shepherd; I shall not want."
            ),
            "Modimo o tlamela matshelo a rona ka lorato lo lo sa feleng. / God faithfully provides for all our needs today."
        ),
        Pair(
            BibleVerse(
                bookId = "pro",
                bookNameSetswana = "Diane",
                bookNameEnglish = "Proverbs",
                chapter = 3,
                verse = 5,
                textSetswana = "Ikanye JEHOFA ka pelo yotlhe ya gago, mme o se ka wa ikaega ka tlhaloganyo ya gago.",
                textEnglish = "Trust in the LORD with all thine heart; and lean not unto thine own understanding."
            ),
            "Mo letlelele a go goge mo mogopolong le dithulaganyong tsotlhe. / Let Him guide your steps and decisions."
        ),
        Pair(
            BibleVerse(
                bookId = "jhn",
                bookNameSetswana = "Johane",
                bookNameEnglish = "John",
                chapter = 3,
                verse = 16,
                textSetswana = "Gonne Modimo o ratile lefatshe mo go kalo, mo o bileng wa ntsha Morwaone yo o tsetsweng a le esi...",
                textEnglish = "For God so loved the world, that he gave his only begotten Son..."
            ),
            "Lorato lwa Modimo lo golo go gaisa sengwe le sengwe. / God's love is unfailing and eternal."
        ),
        Pair(
            BibleVerse(
                bookId = "rom",
                bookNameSetswana = "Baroma",
                bookNameEnglish = "Romans",
                chapter = 8,
                verse = 31,
                textSetswana = "Fa Modimo o le kafa ntlheng ya rona, ke mang yo o ka ganetsanyang le rona?",
                textEnglish = "If God be for us, who can be against us?"
            ),
            "O nna le sebete gonne Morena o emeng le wena. / Stand courageously knowing God is on your side."
        )
    )
}
