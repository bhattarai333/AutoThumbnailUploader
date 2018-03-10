package Enums;

public enum Games {
    SMASH_64(){
        public String toString(){return "Smash 64";}
    },
    SMASH_WIIU(){
        @Override
        public String toString(){
            return "Smash WiiU";
        }
    },
    SMASH_3DS(){
        @Override
        public String toString(){
            return "Smash 3DS";
        }
    },
    SMASH_WIIU_FULL(){
        @Override
        public String toString(){
            return "Smash WiiU Full(WIP)";
        }
    },
    SMASH_MELEE(){
        @Override
        public String toString(){
            return "Smash Melee";
        }
    },
    SMASH_BRAWL(){
        @Override
        public String toString(){
            return "Smash Brawl";
        }
    },
    PROJECT_M(){
        @Override
        public String toString(){
            return "Project M";
        }
    },
    RIVALS_OF_AETHER(){
        @Override
        public String toString(){
            return "Rivals of Aether";
        }
    },
    SHREK(){
        @Override
        public String toString(){
            return "Shrek Super Slam";
        }
    };
    public String getCode(){
        switch(this){
            case SMASH_64: return "64";
            case SMASH_MELEE: return "Melee";
            case SMASH_BRAWL: return "Brawl";
            case PROJECT_M: return "PM";
            case SMASH_3DS: return "3DS";
            case SMASH_WIIU: return "WiiU";
            case SMASH_WIIU_FULL: return "WiiUFull";
            case RIVALS_OF_AETHER: return "RoA";
            case SHREK: return "S3";
            default: return "WiiU";
        }
    }

    public String[] getArray(Boolean isSecondary) {
        switch (this){
            case SMASH_64:
                if(isSecondary){
                    return new String[]{""};
                }else{
                    return new String[]{""};
                }
            case SMASH_MELEE:
                if(isSecondary){
                    return new String[]{"0Nothing","Bowser","Donkey Kong","Doctor Mario","Falco",
                            "Captain Falcon","Fox","Mr. Game & Watch","Ganondorf", "Ice Climbers","Kirby",
                            "Link","Luigi","Mario","Marth","MewTwo","Ness","Peach","Pichu","Pikachu",
                            "Jigglypuff","Roy","Samus","Sheik","Yoshi","Young Link", "Zelda"
                    };
                }else{
                    return new String[]{"Bowser","Donkey Kong","Doctor Mario","Falco",
                            "Captain Falcon","Fox","Mr. Game & Watch","Ganondorf", "Ice Climbers","Kirby",
                            "Link","Luigi","Mario","Marth","MewTwo","Ness","Peach","Pichu","Pikachu",
                            "Jigglypuff","Roy","Samus","Sheik","Yoshi","Young Link", "Zelda"
                    };
                }
            case SMASH_BRAWL:
                if(isSecondary){
                    return new String[]{"0Nothing", "Bowser", "Captain Falcon", "Diddy Kong", "Donkey Kong", "Falco",
                            "Fox", "Ganondorf", "Ice Climbers", "Ike", "Jigglypuff", "King DeDeDe", "Kirby", "Link", "Lucario",
                            "Lucas", "Luigi", "Mario", "Marth", "Meta Knight", "Ness", "Olimar", "Peach", "Pikachu", "Pit",
                            "Pokemon Trainer", "ROB", "Samus", "Sheik", "Snake", "Sonic", "Toon Link", "Wario", "Wolf", "Yoshi",
                            "Zelda"

                    };
                }else{
                    return new String[]{"Bowser", "Captain Falcon", "Diddy Kong", "Donkey Kong", "Falco", "Fox",
                            "Ganondorf", "Ice Climbers", "Ike", "Jigglypuff", "King DeDeDe", "Kirby", "Link", "Lucario", "Lucas",
                            "Luigi", "Mario", "Marth", "Meta Knight", "Ness", "Olimar", "Peach", "Pikachu", "Pit",
                            "Pokemon Trainer", "ROB", "Samus", "Sheik", "Snake", "Sonic", "Toon Link", "Wario", "Wolf", "Yoshi",
                            "Zelda"

                    };
                }
            case PROJECT_M:
                if(isSecondary){
                    return new String[]{""};
                }else {
                    return new String[]{""};
                }
            case SMASH_3DS:
            case SMASH_WIIU_FULL:
            case SMASH_WIIU:
                if(isSecondary){
                    return new String[]{ "0Nothing", "Mario","Luigi","Peach","Bowser","Yoshi",
                            "Rosalina & Luma","Bowser Jr.","Wario","Donkey Kong","Diddy Kong",
                            "Mr. Game and Watch","Little Mac","Link","Zelda","Sheik","Ganondorf",
                            "Toon Link","Samus","Zamus","Pit","Palutena","Marth","Ike","Robin",
                            "Duck Hunt","Kirby","DDD","Meta Knight","Fox","Falco","Pikachu",
                            "Charizard","Lucario","Puff","Greninja","R.O.B.","Ness","Captain Falcon",
                            "Villager","Olimar","Wii Fit","Shulk","Doctor Mario","Dank Pit","Lucina","Pacman",
                            "Megaman","Sonic","MewTwo","Lucas","Roy","Ryu","Cloud","Corrin",
                            "Bayonetta","Mii Gunner","Mii Brawler","Mii Sword"
                    };
                }else {
                    return new String[]{ "Mario","Luigi","Peach","Bowser","Yoshi",
                            "Rosalina & Luma","Bowser Jr.","Wario","Donkey Kong","Diddy Kong",
                            "Mr. Game and Watch","Little Mac","Link","Zelda","Sheik","Ganondorf",
                            "Toon Link","Samus","Zamus","Pit","Palutena","Marth","Ike","Robin",
                            "Duck Hunt","Kirby","DDD","Meta Knight","Fox","Falco","Pikachu",
                            "Charizard","Lucario","Puff","Greninja","R.O.B.","Ness","Captain Falcon",
                            "Villager","Olimar","Wii Fit","Shulk","Doctor Mario","Dank Pit","Lucina","Pacman",
                            "Megaman","Sonic","MewTwo","Lucas","Roy","Ryu","Cloud","Corrin",
                            "Bayonetta","Mii Gunner","Mii Brawler","Mii Sword"
                    };
                }
            case RIVALS_OF_AETHER:
                if(isSecondary){
                    return new String[]{"0Nothing", "Zetterburn", "Orcane", "Wrastor", "Kragg",
                            "Forsburn", "Maypul", "Absa", "Etalus"
                    };
                }else{
                    return new String[]{"Zetterburn", "Orcane", "Wrastor", "Kragg",
                            "Forsburn", "Maypul", "Absa", "Etalus"
                    };
                }
            case SHREK:
                if(isSecondary){
                    return new String[]{"0Nothing", "Shrek", "Donkey", "Puss", "Gingy",
                            "Princess Fiona", "Ogre Fiona", "Pinocchio", "Prince Charming", "Red",
                            "Black Knight", "Wolf", "Anthrax", "Cyclops", "Robin Hood", "G~Nome",
                            "Dronkey", "Quasimodo", "Luna", "Captain Hook", "Humptey Dumptey"
                    };
                }else{
                    return new String[]{"Shrek", "Donkey", "Puss", "Gingy",
                            "Princess Fiona", "Ogre Fiona", "Pinocchio", "Prince Charming", "Red",
                            "Black Knight", "Wolf", "Anthrax", "Cyclops", "Robin Hood", "G~Nome",
                            "Dronkey", "Quasimodo", "Luna", "Captain Hook", "Humptey Dumptey"

                    };
                }
            default:
                return new String[]{""};
        }

    }
}
