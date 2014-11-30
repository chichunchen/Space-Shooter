JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
		  MainMenu.java
#		  Audio.java \
		  NormalCraft.java \
          Board.java \
		  PVPChooseCraft.java \
          ChooseGroup.java \
		  Rank.java \
          Craft.java \
		  Records.java \
          EnemyCraft.java \
		  SendingObject.java \
          EnemyGroup.java \
		  SpaceObject.java \
          HeroGroup.java \
		  StageChooseCraft.java \
          Item.java \
		  StageMode.java \
          MainMenu.java \
		  Stone.java \
          MenuPanel.java \
		  Tips.java \
          Missile.java

default: classes

classes: $(CLASSES:.java=.class)

#
# RM is a predefined macro in make (RM = rm -f)
#

clean:
	$(RM) *.class
