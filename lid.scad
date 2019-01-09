include <Thread_Library.scad>
use <write.scad>
union () {
  difference () {
    cylinder ($fn=120, h=13, r=25.75);
    trapezoidThreadNegativeSpace (threadAngle=30, threadHeightToPitch=0.24000000000000002, backlash=0.1, clearance=0.1, pitch=4.25, stepsPerTurn=90, length=13, pitchRadius=22.75, countersunk=0, RH=true, profileRatio=1.66);
    union () {
      rotate ([0.0,180.0,0.0]) {
        writecylinder ("SPICECOOP", [0, 0, -13], 25.75, 13, h=9, t=1.5);
      }
      rotate ([0.0,180.0,180.0]) {
        writecylinder ("SPICECOOP", [0, 0, -13], 25.75, 13, h=9, t=1.5);
      }
    }
  }
  difference () {
    cylinder ($fn=120, h=3.6, r=25.75);
    union () {
      rotate ([0.0,180.0,0.0]) {
        writecylinder ("SPICECOOP", [0, 0, -13], 25.75, 13, h=9, t=1.5);
      }
      rotate ([0.0,180.0,180.0]) {
        writecylinder ("SPICECOOP", [0, 0, -13], 25.75, 13, h=9, t=1.5);
      }
    }
  }
}
