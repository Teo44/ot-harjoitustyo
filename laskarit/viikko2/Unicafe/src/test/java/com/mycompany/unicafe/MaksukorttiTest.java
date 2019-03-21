package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void saldoAlussaOikein() {
        assertEquals(10.0, kortti.saldo());
    }
    
    @Test
    public void saldonLataaminenToimiiOikein()  {
        kortti.lataaRahaa(10);
        assertEquals(20.0, kortti.saldo());
    }
    
    @Test
    public void rahanOttaminenToimii()  {
        kortti.otaRahaa(5);
        assertEquals(5.0, kortti.saldo());
    }
    
    @Test
    public void rahanOttaminenBooleanToimii()   {
        assertTrue(kortti.otaRahaa(5));
    }
    
    @Test
    public void rahanOttaminenKunSaldoEiRiita() {
        kortti.otaRahaa(5);
        kortti.otaRahaa(6);
        assertEquals(5.0, kortti.saldo());
    }
    
    @Test
    public void rahanOttaminenBooleanKunSaldoEiRiita()  {
        kortti.otaRahaa(5);
        assertFalse(kortti.otaRahaa(6));
    }
    
}









