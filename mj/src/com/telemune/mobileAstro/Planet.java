package com.telemune.mobileAstro;
/*    */ 
/*    */ import java.util.EnumSet;
/*    */ import java.util.Hashtable;
/*    */ 
/*    */ public enum Planet
/*    */ {


	/* 16 */   Ketu(0, 0, 0, 0), Venus(2, 7, 0, 0), Sun(5, 0, 0, 0), Moon(4, 0, 0, 0), Mars(1, 8, 3, 7), Rahu(0, 0, 0, 0), Jupiter(9, 12, 4, 8), Saturn(10, 11, 2, 9), Mercury(3, 6, 0, 0), UNKNOWN(0, 0, 0, 0);
	/*    */ 
	/*    */   private static final Hashtable<Integer, Planet> lookup;
	/*    */   private final int firstRashi;
	/*    */   private final int secondRashi;
	/*    */   private final int firstAspect;
	/*    */   private final int secondAspect;
	/*    */
		//	System.out.println("INSIDE ENUM PLANET... LETS BEGIN THE SHOW #########"); 
	/* 18 */   static { lookup = new Hashtable();
		/*    */ System.out.println("INSIDE STATIC >> ");
		/* 67 */     for (Planet planet : EnumSet.allOf(Planet.class)) {
			/* 68 */       lookup.put(Integer.valueOf(planet.getFirstRashi()), planet);
			/* 69 */       if (planet.getSecondRashi() == 0)
				/*    */         continue;
			/* 71 */       lookup.put(Integer.valueOf(planet.getSecondRashi()), planet);
			/*    */     }
			
				System.out.println("lookup>> "+lookup);

		/*    */   }
	/*    */ 
	/*    */   private Planet(int _firstRashi, int _secondRashi, int _firstAspect, int _secondAspect)
		/*    */   {
				//	System.out.println("INSIDE PLANET CONS >>>> _firstRashi= "+_firstRashi+" ,_secondRashi=> "+_secondRashi+"  ,_firstAspect=> "+_firstAspect+"  ,_secondAspect=> "+_secondAspect);
			/* 29 */     this.firstRashi = _firstRashi;
			/* 30 */     this.secondRashi = _secondRashi;
			/* 31 */     this.firstAspect = _firstAspect;
			/* 32 */     this.secondAspect = _secondAspect;
			/*    */   }
	/*    */ 
	/*    */   public int getFirstRashi()
		/*    */   {
			/* 37 */     return this.firstRashi;
			/*    */   }
	/*    */ 
	/*    */   public int getSecondRashi()
		/*    */   {
			/* 43 */     return this.secondRashi;
			/*    */   }
	/*    */ 
	/*    */   public int getFirstAspect()
		/*    */   {
			/* 48 */     return this.firstAspect;
			/*    */   }
	/*    */ 
	/*    */   public int getSecondAspect()
		/*    */   {
			/* 54 */     return this.secondAspect;
			/*    */   }
	/*    */ 
	/*    */   public static Planet toPlanets(String _planetString)
		/*    */   {
				//System.out.println("INSIDE TOPLANETS>> _planetString=>> "+_planetString);
			/* 61 */     return valueOf(_planetString);
			/*    */   }
	/*    */ 
	/*    */   public static Planet fromOrdinal(int ordinal)
		/*    */   {
			//	System.out.println("INSIDE fromOrdinal>> ordinal=>> "+ordinal);
			/* 78 */     return (Planet)lookup.get(Integer.valueOf(ordinal));
			/*    */   }
	/*    */ }

	/* Location:           C:\Users\SUCCESS\Downloads\kundli.jar
	 * Qualified Name:     com.telemune.astro.Planet
	 * JD-Core Version:    0.6.0
	 */
