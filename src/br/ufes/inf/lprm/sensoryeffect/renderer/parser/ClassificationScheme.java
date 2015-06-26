/*
 * This file is part of SEVino.
 *
 * SEVino is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SEVino is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SEVino.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Copyright 2008, 2009, 2010, 2011, 2012 Alpen-Adria-Universitaet Klagenfurt, Markus Waltl.
 */
package br.ufes.inf.lprm.sensoryeffect.renderer.parser;

import java.awt.Color;
import java.util.Vector;
import java.util.Hashtable;

/**
 * Class defining all classification schemes of MPEG-V
 * @author Markus Waltl
 *
 */
public class ClassificationScheme {
	// A.2.1	ColorCS
	private static Hashtable<String, Color> colorTable_ = new Hashtable<String, Color>();
	
	public final static String COLORURIBASE = "urn:mpeg:mpeg-v:01-SI-ColorCS-NS";	
	public final static String COLOR_ALICE_BLUE = COLORURIBASE + ":alice_blue";
	public final static String COLOR_ALIZARIN = COLORURIBASE + ":alizarin";
	public final static String COLOR_AMARANTH = COLORURIBASE + ":amaranth";
	public final static String COLOR_AMARANTH_PINK = COLORURIBASE + ":amaranth_pink";
	public final static String COLOR_AMBER = COLORURIBASE + ":amber";
	public final static String COLOR_AMETHYST = COLORURIBASE + ":amethyst";
	public final static String COLOR_APRICOT = COLORURIBASE + ":apricot";
	public final static String COLOR_AQUA = COLORURIBASE + ":aqua";
	public final static String COLOR_AQUAMARINE = COLORURIBASE + ":aquamarine";
	public final static String COLOR_ARMY_GREEN = COLORURIBASE + ":army_green";
	public final static String COLOR_ASPARAGUS = COLORURIBASE + ":asparagus";
	public final static String COLOR_ATOMIC_TANGERINE = COLORURIBASE + ":atomic_tangerine";
	public final static String COLOR_AUBURN = COLORURIBASE + ":auburn";
	public final static String COLOR_AZURE_COLOR_WHEEL = COLORURIBASE + ":azure_color_wheel";
	public final static String COLOR_AZURE_WEB = COLORURIBASE + ":azure_web";
	public final static String COLOR_BABY_BLUE = COLORURIBASE + ":baby_blue";
	public final static String COLOR_BEIGE = COLORURIBASE + ":beige";
	public final static String COLOR_BISTRE = COLORURIBASE + ":bistre";
	public final static String COLOR_BLACK = COLORURIBASE + ":black";
	public final static String COLOR_BLUE = COLORURIBASE + ":blue";
	public final static String COLOR_BLUE_PIGMENT = COLORURIBASE + ":blue_pigment";
	public final static String COLOR_BLUE_RYB = COLORURIBASE + ":blue_ryb";
	public final static String COLOR_BLUE_GREEN = COLORURIBASE + ":blue-green";
	public final static String COLOR_BLUE_VIOLET = COLORURIBASE + ":blue-violet";
	public final static String COLOR_BONDI_BLUE = COLORURIBASE + ":bondi_blue";
	public final static String COLOR_BRASS = COLORURIBASE + ":brass";
	public final static String COLOR_BRIGHT_GREEN = COLORURIBASE + ":bright_green";
	public final static String COLOR_BRIGHT_PINK = COLORURIBASE + ":bright_pink";
	public final static String COLOR_BRIGHT_TURQUOISE = COLORURIBASE + ":bright_turquoise";
	public final static String COLOR_BRILLIANT_ROSE = COLORURIBASE + ":brilliant_rose";
	public final static String COLOR_BRINK_PINK = COLORURIBASE + ":brink_pink";
	public final static String COLOR_BRONZE = COLORURIBASE + ":bronze"; 
	public final static String COLOR_BROWN = COLORURIBASE + ":brown";
	public final static String COLOR_BUFF = COLORURIBASE + ":buff";
	public final static String COLOR_BURGUNDY = COLORURIBASE + ":burgundy";
	public final static String COLOR_BURNT_ORANGE = COLORURIBASE + ":burnt_orange";
	public final static String COLOR_BURNT_SIENNA = COLORURIBASE + ":burnt_sienna";
	public final static String COLOR_BURNT_UMBER = COLORURIBASE + ":burnt_umber";
	public final static String COLOR_CAMOUFLAGE_GREEN = COLORURIBASE + ":camouflage_green";
	public final static String COLOR_CAPUT_MORTUUM = COLORURIBASE + ":caput_mortuum";
	public final static String COLOR_CARDINAL = COLORURIBASE + ":cardinal";
	public final static String COLOR_CARMINE = COLORURIBASE + ":carmine";
	public final static String COLOR_CARMINE_PINK = COLORURIBASE + ":carmine_pink";
	public final static String COLOR_CARNATION_PINK = COLORURIBASE + ":carnation_pink";
	public final static String COLOR_CAROLINA_BLUE = COLORURIBASE + ":carolina_blue";
	public final static String COLOR_CARROT_ORANGE = COLORURIBASE + ":carrot_orange";
	public final static String COLOR_CELADON = COLORURIBASE + ":celadon";
	public final static String COLOR_CERISE = COLORURIBASE + ":cerise";
	public final static String COLOR_CERISE_PINK = COLORURIBASE + ":cerise_pink";
	public final static String COLOR_CERULEAN = COLORURIBASE + ":cerulean";
	public final static String COLOR_CERULEAN_BLUE = COLORURIBASE + ":cerulean_blue";
	public final static String COLOR_CHAMPAGNE = COLORURIBASE + ":champagne";
	public final static String COLOR_CHARCOAL = COLORURIBASE + ":charcoal";
	public final static String COLOR_CHARTREUSE_TRADITIONAL = COLORURIBASE + ":chartreuse_traditional";
	public final static String COLOR_CHARTREUSE_WEB = COLORURIBASE + ":chartreuse_web";
	public final static String COLOR_CHERRY_BLOSSOM_PINK = COLORURIBASE + ":cherry_blossom_pink";
	public final static String COLOR_CHESTNUT = COLORURIBASE + ":chestnut";
	public final static String COLOR_CHOCOLATE = COLORURIBASE + ":chocolate";
	public final static String COLOR_CINNABAR = COLORURIBASE + ":cinnabar";
	public final static String COLOR_CINNAMON = COLORURIBASE + ":cinnamon";
	public final static String COLOR_COBALT = COLORURIBASE + ":cobalt";
	public final static String COLOR_COLUMBIA_BLUE = COLORURIBASE + ":columbia_blue";
	public final static String COLOR_COPPER = COLORURIBASE + ":copper";
	public final static String COLOR_COPPER_ROSE = COLORURIBASE + ":copper_rose";
	public final static String COLOR_CORAL = COLORURIBASE + ":coral";
	public final static String COLOR_CORAL_PINK = COLORURIBASE + ":coral_pink";
	public final static String COLOR_CORAL_RED = COLORURIBASE + ":coral_red";
	public final static String COLOR_CORN = COLORURIBASE + ":corn";
	public final static String COLOR_CORNFLOWER_BLUE = COLORURIBASE + ":cornflower_blue";
	public final static String COLOR_COSMIC_LATTE = COLORURIBASE + ":cosmic_latte";
	public final static String COLOR_CREAM = COLORURIBASE + ":cream";
	public final static String COLOR_CRIMSON = COLORURIBASE + ":crimson";
	public final static String COLOR_CYAN = COLORURIBASE + ":cyan";
	public final static String COLOR_CYAN_PROCESS = COLORURIBASE + ":cyan_process";
	public final static String COLOR_DARK_BLUE = COLORURIBASE + ":dark_blue";
	public final static String COLOR_DARK_BROWN = COLORURIBASE + ":dark_brown";
	public final static String COLOR_DARK_CERULEAN = COLORURIBASE + ":dark_cerulean";
	public final static String COLOR_DARK_CHESTNUT = COLORURIBASE + ":dark_chestnut";
	public final static String COLOR_DARK_CORAL = COLORURIBASE + ":dark_coral";
	public final static String COLOR_DARK_GOLDENROD = COLORURIBASE + ":dark_goldenrod";
	public final static String COLOR_DARK_GREEN = COLORURIBASE + ":dark_green";
	public final static String COLOR_DARK_KHAKI = COLORURIBASE + ":dark_khaki";
	public final static String COLOR_DARK_MAGENTA = COLORURIBASE + ":dark_magenta";
	public final static String COLOR_DARK_PASTEL_GREEN = COLORURIBASE + ":dark_pastel_green";
	public final static String COLOR_DARK_PINK = COLORURIBASE + ":dark_pink";
	public final static String COLOR_DARK_SCARLET = COLORURIBASE + ":dark_scarlet";
	public final static String COLOR_DARK_SALMON = COLORURIBASE + ":dark_salmon";
	public final static String COLOR_DARK_SLATE_GRAY = COLORURIBASE + ":dark_slate_gray";
	public final static String COLOR_DARK_SPRING_GREEN = COLORURIBASE + ":dark_spring_green";
	public final static String COLOR_DARK_TAN = COLORURIBASE + ":dark_tan";
	public final static String COLOR_DARK_TURQUOISE = COLORURIBASE + ":dark_turquoise";
	public final static String COLOR_DARK_VIOLET = COLORURIBASE + ":dark_violet";
	public final static String COLOR_DEEP_CARMINE_PINK = COLORURIBASE + ":deep_carmine_pink";
	public final static String COLOR_DEEP_CERISE = COLORURIBASE + ":deep_cerise";
	public final static String COLOR_DEEP_CHESTNUT = COLORURIBASE + ":deep_chestnut";
	public final static String COLOR_DEEP_FUCHSIA = COLORURIBASE + ":deep_fuchsia";
	public final static String COLOR_DEEP_LILAC = COLORURIBASE + ":deep_lilac";
	public final static String COLOR_DEEP_MAGENTA = COLORURIBASE + ":deep_magenta";
	public final static String COLOR_DEEP_PEACH = COLORURIBASE + ":deep_peach";
	public final static String COLOR_DEEP_PINK = COLORURIBASE + ":deep_pink";
	public final static String COLOR_DENIM = COLORURIBASE + ":denim";
	public final static String COLOR_DODGER_BLUE = COLORURIBASE + ":dodger_blue";
	public final static String COLOR_ECRU = COLORURIBASE + ":ecru";
	public final static String COLOR_EGYPTIAN_BLUE = COLORURIBASE + ":egyptian_blue";
	public final static String COLOR_ELECTRIC_BLUE = COLORURIBASE + ":electric_blue";
	public final static String COLOR_ELECTRIC_GREEN_X11_GREEN = COLORURIBASE + ":electric_green_x11_green";
	public final static String COLOR_ELECTRIC_INDIGO = COLORURIBASE + ":electric_indigo";
	public final static String COLOR_ELECTRIC_LIME = COLORURIBASE + ":electric_lime";
	public final static String COLOR_ELECTRIC_PURPLE = COLORURIBASE + ":electric_purple";
	public final static String COLOR_EMERALD = COLORURIBASE + ":emerald";
	public final static String COLOR_EGGPLANT = COLORURIBASE + ":eggplant";
	public final static String COLOR_FALU_RED = COLORURIBASE + ":falu_red";
	public final static String COLOR_FERN_GREEN = COLORURIBASE + ":fern_green";
	public final static String COLOR_FIREBRICK = COLORURIBASE + ":firebrick";
	public final static String COLOR_FLAX = COLORURIBASE + ":flax";
	public final static String COLOR_FOREST_GREEN = COLORURIBASE + ":forest_green";
	public final static String COLOR_FRENCH_ROSE = COLORURIBASE + ":french_rose";
	public final static String COLOR_FUCHSIA = COLORURIBASE + ":fuchsia";
	public final static String COLOR_FUCHSIA_PINK = COLORURIBASE + ":fuchsia_pink";
	public final static String COLOR_GAMBOGE = COLORURIBASE + ":gamboge";
	public final static String COLOR_GOLD_METALLIC = COLORURIBASE + ":gold_metallic";
	public final static String COLOR_GOLD_WEB_GOLDEN = COLORURIBASE + ":gold_web_golden";
	public final static String COLOR_GOLDEN_BROWN = COLORURIBASE + ":golden_brown";
	public final static String COLOR_GOLDEN_YELLOW = COLORURIBASE + ":golden_yellow";
	public final static String COLOR_GOLDENROD = COLORURIBASE + ":goldenrod";
	public final static String COLOR_GREY_ASPARAGUS = COLORURIBASE + ":grey-asparagus";
	public final static String COLOR_GREEN_COLOR_WHEEL_X11_GREEN = COLORURIBASE + ":green_color_wheel_x11_green";
	public final static String COLOR_GREEN_HTMLCSS_GREEN = COLORURIBASE + ":green_html/css_green";
	public final static String COLOR_GREEN_PIGMENT = COLORURIBASE + ":green_pigment";
	public final static String COLOR_GREEN_RYB = COLORURIBASE + ":green_ryb";
	public final static String COLOR_GREEN_YELLOW = COLORURIBASE + ":green-yellow";
	public final static String COLOR_GREY = COLORURIBASE + ":grey";
	public final static String COLOR_HAN_PURPLE = COLORURIBASE + ":han_purple";
	public final static String COLOR_HARLEQUIN = COLORURIBASE + ":harlequin";
	public final static String COLOR_HELIOTROPE = COLORURIBASE + ":heliotrope";
	public final static String COLOR_HOLLYWOOD_CERISE = COLORURIBASE + ":hollywood_cerise";
	public final static String COLOR_HOT_MAGENTA = COLORURIBASE + ":hot_magenta";
	public final static String COLOR_HOT_PINK = COLORURIBASE + ":hot_pink";
	public final static String COLOR_INDIGO_DYE = COLORURIBASE + ":indigo_dye";
	public final static String COLOR_INDIGO_WEB = COLORURIBASE + ":indigo_web";
	public final static String COLOR_INTERNATIONAL_KLEIN_BLUE = COLORURIBASE + ":international_klein_blue";
	public final static String COLOR_INTERNATIONAL_ORANGE = COLORURIBASE + ":international_orange";
	public final static String COLOR_ISLAMIC_GREEN = COLORURIBASE + ":islamic_green";
	public final static String COLOR_IVORY = COLORURIBASE + ":ivory";
	public final static String COLOR_JADE = COLORURIBASE + ":jade";
	public final static String COLOR_KELLY_GREEN = COLORURIBASE + ":kelly_green";
	public final static String COLOR_KHAKI = COLORURIBASE + ":khaki";
	public final static String COLOR_KHAKI_X11_LIGHT_KHAKI = COLORURIBASE + ":khaki_x11_light_khaki";
	public final static String COLOR_LAVENDER_FLORAL = COLORURIBASE + ":lavender_floral";
	public final static String COLOR_LAVENDER_WEB = COLORURIBASE + ":lavender_web";
	public final static String COLOR_LAVENDER_BLUE = COLORURIBASE + ":lavender_blue";
	public final static String COLOR_LAVENDER_BLUSH = COLORURIBASE + ":lavender_blush";
	public final static String COLOR_LAVENDER_GREY = COLORURIBASE + ":lavender_grey";
	public final static String COLOR_LAVENDER_MAGENTA = COLORURIBASE + ":lavender_magenta";
	public final static String COLOR_LAVENDER_PINK = COLORURIBASE + ":lavender_pink";
	public final static String COLOR_LAVENDER_PURPLE = COLORURIBASE + ":lavender_purple";
	public final static String COLOR_LAVENDER_ROSE = COLORURIBASE + ":lavender_rose";
	public final static String COLOR_LAWN_GREEN = COLORURIBASE + ":lawn_green";
	public final static String COLOR_LEMON = COLORURIBASE + ":lemon";
	public final static String COLOR_LEMON_CHIFFON = COLORURIBASE + ":lemon_chiffon";
	public final static String COLOR_LIGHT_BLUE = COLORURIBASE + ":light_blue";
	public final static String COLOR_LIGHT_PINK = COLORURIBASE + ":light_pink";
	public final static String COLOR_LILAC = COLORURIBASE + ":lilac";
	public final static String COLOR_LIME_COLOR_WHEEL = COLORURIBASE + ":lime_color_wheel";
	public final static String COLOR_LIME_WEB_X11_GREEN = COLORURIBASE + ":lime_web_x11_green";
	public final static String COLOR_LIME_GREEN = COLORURIBASE + ":lime_green";
	public final static String COLOR_LINEN = COLORURIBASE + ":linen";
	public final static String COLOR_MAGENTA = COLORURIBASE + ":magenta";
	public final static String COLOR_MAGENTA_DYE = COLORURIBASE + ":magenta_dye";
	public final static String COLOR_MAGENTA_PROCESS = COLORURIBASE + ":magenta_process";
	public final static String COLOR_MAGIC_MINT = COLORURIBASE + ":magic_mint";
	public final static String COLOR_MAGNOLIA = COLORURIBASE + ":magnolia";
	public final static String COLOR_MALACHITE = COLORURIBASE + ":malachite";
	public final static String COLOR_MAROON_HTMLCSS = COLORURIBASE + ":maroon_html/css";
	public final static String COLOR_MAROON_X11 = COLORURIBASE + ":maroon_x11";
	public final static String COLOR_MAYA_BLUE = COLORURIBASE + ":maya_blue";
	public final static String COLOR_MAUVE = COLORURIBASE + ":mauve";
	public final static String COLOR_MAUVE_TAUPE = COLORURIBASE + ":mauve_taupe";
	public final static String COLOR_MEDIUM_BLUE = COLORURIBASE + ":medium_blue";
	public final static String COLOR_MEDIUM_CARMINE = COLORURIBASE + ":medium_carmine";
	public final static String COLOR_MEDIUM_LAVENDER_MAGENTA = COLORURIBASE + ":medium_lavender_magenta";
	public final static String COLOR_MEDIUM_PURPLE = COLORURIBASE + ":medium_purple";
	public final static String COLOR_MEDIUM_SPRING_GREEN = COLORURIBASE + ":medium_spring_green";
	public final static String COLOR_MIDNIGHT_BLUE = COLORURIBASE + ":midnight_blue";
	public final static String COLOR_MIDNIGHT_GREEN_EAGLE_GREEN = COLORURIBASE + ":midnight_green_eagle_green";
	public final static String COLOR_MINT_GREEN = COLORURIBASE + ":mint_green";
	public final static String COLOR_MISTY_ROSE = COLORURIBASE + ":misty_rose";
	public final static String COLOR_MOSS_GREEN = COLORURIBASE + ":moss_green";
	public final static String COLOR_MOUNTBATTEN_PINK = COLORURIBASE + ":mountbatten_pink";
	public final static String COLOR_MUSTARD = COLORURIBASE + ":mustard";
	public final static String COLOR_MYRTLE = COLORURIBASE + ":myrtle";
	public final static String COLOR_NAVAJO_WHITE = COLORURIBASE + ":navajo_white";
	public final static String COLOR_NAVY_BLUE = COLORURIBASE + ":navy_blue";
	public final static String COLOR_OCHRE = COLORURIBASE + ":ochre";
	public final static String COLOR_OFFICE_GREEN = COLORURIBASE + ":office_green";
	public final static String COLOR_OLD_GOLD = COLORURIBASE + ":old_gold";
	public final static String COLOR_OLD_LACE = COLORURIBASE + ":old_lace";
	public final static String COLOR_OLD_LAVENDER = COLORURIBASE + ":old_lavender";
	public final static String COLOR_OLD_ROSE = COLORURIBASE + ":old_rose";
	public final static String COLOR_OLIVE = COLORURIBASE + ":olive";
	public final static String COLOR_OLIVE_DRAB = COLORURIBASE + ":olive_drab";
	public final static String COLOR_OLIVINE = COLORURIBASE + ":olivine";
	public final static String COLOR_ORANGE_COLOR_WHEEL = COLORURIBASE + ":orange_color_wheel";
	public final static String COLOR_ORANGE_RYB = COLORURIBASE + ":orange_ryb";
	public final static String COLOR_ORANGE_WEB = COLORURIBASE + ":orange_web";
	public final static String COLOR_ORANGE_PEEL = COLORURIBASE + ":orange_peel";
	public final static String COLOR_ORANGE_RED = COLORURIBASE + ":orange-red";	    
	public final static String COLOR_ORCHID = COLORURIBASE + ":orchid";	    
	public final static String COLOR_PALE_BLUE = COLORURIBASE + ":pale_blue";	    
	public final static String COLOR_PALE_BROWN = COLORURIBASE + ":pale_brown";	    
	public final static String COLOR_PALE_CARMINE = COLORURIBASE + ":pale_carmine";	    
	public final static String COLOR_PALE_CHESTNUT = COLORURIBASE + ":pale_chestnut";	    
	public final static String COLOR_PALE_CORNFLOWER_BLUE = COLORURIBASE + ":pale_cornflower_blue";	    
	public final static String COLOR_PALE_MAGENTA = COLORURIBASE + ":pale_magenta";	    
	public final static String COLOR_PALE_PINK = COLORURIBASE + ":pale_pink";	    
	public final static String COLOR_PALE_RED_VIOLET = COLORURIBASE + ":pale_red-violet";	    
	public final static String COLOR_PAPAYA_WHIP = COLORURIBASE + ":papaya_whip";	    
	public final static String COLOR_PASTEL_GREEN = COLORURIBASE + ":pastel_green";	    
	public final static String COLOR_PASTEL_PINK = COLORURIBASE + ":pastel_pink";
	public final static String COLOR_PEACH = COLORURIBASE + ":peach";	    
	public final static String COLOR_PEACH_ORANGE = COLORURIBASE + ":peach-orange";	    
	public final static String COLOR_PEACH_YELLOW = COLORURIBASE + ":peach-yellow";	    
	public final static String COLOR_PEAR = COLORURIBASE + ":pear";	    
	public final static String COLOR_PERIWINKLE = COLORURIBASE + ":periwinkle";	    
	public final static String COLOR_PERSIAN_BLUE = COLORURIBASE + ":persian_blue";	    
	public final static String COLOR_PERSIAN_GREEN = COLORURIBASE + ":persian_green";	    
	public final static String COLOR_PERSIAN_INDIGO = COLORURIBASE + ":persian_indigo";	    
	public final static String COLOR_PERSIAN_ORANGE = COLORURIBASE + ":persian_orange";	    
	public final static String COLOR_PERSIAN_RED = COLORURIBASE + ":persian_red";	    
	public final static String COLOR_PERSIAN_PINK = COLORURIBASE + ":persian_pink";	    
	public final static String COLOR_PERSIAN_ROSE = COLORURIBASE + ":persian_rose";	    
	public final static String COLOR_PERSIMMON = COLORURIBASE + ":persimmon";	    
	public final static String COLOR_PINE_GREEN = COLORURIBASE + ":pine_green";	    
	public final static String COLOR_PINK = COLORURIBASE + ":pink";	    
	public final static String COLOR_PINK_ORANGE = COLORURIBASE + ":pink-orange";	    
	public final static String COLOR_PLATINUM = COLORURIBASE + ":platinum";	    
	public final static String COLOR_PLUM_WEB = COLORURIBASE + ":plum_web";	    
	public final static String COLOR_POWDER_BLUE_WEB = COLORURIBASE + ":powder_blue_web";	    
	public final static String COLOR_PUCE = COLORURIBASE + ":puce";	    
	public final static String COLOR_PRUSSIAN_BLUE = COLORURIBASE + ":prussian_blue";	    
	public final static String COLOR_PSYCHEDELIC_PURPLE = COLORURIBASE + ":psychedelic_purple";	    
	public final static String COLOR_PUMPKIN = COLORURIBASE + ":pumpkin";	    
	public final static String COLOR_PURPLE_HTMLCSS = COLORURIBASE + ":purple_html/css";	  	    
	public final static String COLOR_PURPLE_X11 = COLORURIBASE + ":purple_x11";	    
	public final static String COLOR_PURPLE_TAUPE = COLORURIBASE + ":purple_taupe";	    
	public final static String COLOR_RAW_UMBER = COLORURIBASE + ":raw_umber";	    
	public final static String COLOR_RAZZMATAZZ = COLORURIBASE + ":razzmatazz";	    
	public final static String COLOR_RED = COLORURIBASE + ":red";	    
	public final static String COLOR_RED_PIGMENT = COLORURIBASE + ":red_pigment";
	public final static String COLOR_RED_RYB = COLORURIBASE + ":red_ryb";	    
	public final static String COLOR_RED_VIOLET = COLORURIBASE + ":red-violet";	    
	public final static String COLOR_RICH_CARMINE = COLORURIBASE + ":rich_carmine";	    
	public final static String COLOR_ROBIN_EGG_BLUE = COLORURIBASE + ":robin_egg_blue";	    
	public final static String COLOR_ROSE = COLORURIBASE + ":rose";	    
	public final static String COLOR_ROSE_MADDER = COLORURIBASE + ":rose_madder";	    
	public final static String COLOR_ROSE_TAUPE = COLORURIBASE + ":rose_taupe";	    
	public final static String COLOR_ROYAL_BLUE = COLORURIBASE + ":royal_blue";
	public final static String COLOR_ROYAL_PURPLE = COLORURIBASE + ":royal_purple";
	public final static String COLOR_RUBY = COLORURIBASE + ":ruby";
	public final static String COLOR_RUSSET = COLORURIBASE + ":russet";	    
	public final static String COLOR_RUST = COLORURIBASE + ":rust";	    
	public final static String COLOR_SAFETY_ORANGE_BLAZE_ORANGE = COLORURIBASE + ":safety_orange_blaze_orange";	    
	public final static String COLOR_SAFFRON = COLORURIBASE + ":saffron";	    
	public final static String COLOR_SALMON = COLORURIBASE + ":salmon";	    
	public final static String COLOR_SANDY_BROWN = COLORURIBASE + ":sandy_brown";	    
	public final static String COLOR_SANGRIA = COLORURIBASE + ":sangria";	    
	public final static String COLOR_SAPPHIRE = COLORURIBASE + ":sapphire";	    
	public final static String COLOR_SCARLET = COLORURIBASE + ":scarlet";	    
	public final static String COLOR_SCHOOL_BUS_YELLOW = COLORURIBASE + ":school_bus_yellow";	    
	public final static String COLOR_SEA_GREEN = COLORURIBASE + ":sea_green";	    	  
	public final static String COLOR_SEASHELL = COLORURIBASE + ":seashell";	    
	public final static String COLOR_SELECTIVE_YELLOW = COLORURIBASE + ":selective_yellow";	    
	public final static String COLOR_SEPIA = COLORURIBASE + ":sepia";	    
	public final static String COLOR_SHAMROCK_GREEN = COLORURIBASE + ":shamrock_green";	    
	public final static String COLOR_SHOCKING_PINK = COLORURIBASE + ":shocking_pink";	    
	public final static String COLOR_SILVER = COLORURIBASE + ":silver";	    
	public final static String COLOR_SKY_BLUE = COLORURIBASE + ":sky_blue";	    
	public final static String COLOR_SLATE_GREY = COLORURIBASE + ":slate_grey";	    
	public final static String COLOR_SMALT_DARK_POWDER_BLUE = COLORURIBASE + ":smalt_dark_powder_blue";	    
	public final static String COLOR_SPRING_BUD = COLORURIBASE + ":spring_bud";	    
	public final static String COLOR_SPRING_GREEN = COLORURIBASE + ":spring_green";	    
	public final static String COLOR_STEEL_BLUE = COLORURIBASE + ":steel_blue";	    
	public final static String COLOR_TAN = COLORURIBASE + ":tan";	    
	public final static String COLOR_TANGERINE = COLORURIBASE + ":tangerine";	    
	public final static String COLOR_TANGERINE_YELLOW = COLORURIBASE + ":tangerine_yellow";	    
	public final static String COLOR_TAUPE = COLORURIBASE + ":taupe";	    
	public final static String COLOR_TEA_GREEN = COLORURIBASE + ":tea_green";	    
	public final static String COLOR_TEA_ROSE_ORANGE = COLORURIBASE + ":tea_rose_orange";	    
	public final static String COLOR_TEA_ROSE_ROSE = COLORURIBASE + ":tea_rose_rose";	    
	public final static String COLOR_TEAL = COLORURIBASE + ":teal";	    
	public final static String COLOR_TENNY_TAWNY = COLORURIBASE + ":tenny_tawny";	    
	public final static String COLOR_TERRA_COTTA = COLORURIBASE + ":terra_cotta";	    
	public final static String COLOR_THISTLE = COLORURIBASE + ":thistle";	    
	public final static String COLOR_TOMATO = COLORURIBASE + ":tomato";	    
	public final static String COLOR_TURQUOISE = COLORURIBASE + ":turquoise";	    
	public final static String COLOR_TYRIAN_PURPLE = COLORURIBASE + ":tyrian_purple";
	public final static String COLOR_ULTRAMARINE = COLORURIBASE + ":ultramarine";	    
	public final static String COLOR_ULTRA_PINK = COLORURIBASE + ":ultra_pink";	    
	public final static String COLOR_UNITED_NATIONS_BLUE = COLORURIBASE + ":united_nations_blue";	    
	public final static String COLOR_VEGAS_GOLD = COLORURIBASE + ":vegas_gold";	    
	public final static String COLOR_VERMILION = COLORURIBASE + ":vermilion";	    
	public final static String COLOR_VIOLET = COLORURIBASE + ":violet";	    
	public final static String COLOR_VIOLET_WEB = COLORURIBASE + ":violet_web";	    
	public final static String COLOR_VIOLET_RYB = COLORURIBASE + ":violet_ryb";	    
	public final static String COLOR_VIRIDIAN = COLORURIBASE + ":viridian";	    
	public final static String COLOR_WHEAT = COLORURIBASE + ":wheat";
	public final static String COLOR_WHITE = COLORURIBASE + ":white";	    
	public final static String COLOR_WISTERIA = COLORURIBASE + ":wisteria";
	public final static String COLOR_YELLOW = COLORURIBASE + ":yellow";
	public final static String COLOR_YELLOW_PROCESS = COLORURIBASE + ":yellow_process";
	public final static String COLOR_YELLOW_RYB = COLORURIBASE + ":yellow_ryb";
	public final static String COLOR_YELLOW_GREEN = COLORURIBASE + ":yellow-green";
	  	  
	static {
		colorTable_.put(COLOR_ALICE_BLUE, new Color( 240, 248, 255 ));
		colorTable_.put(COLOR_ALIZARIN, new Color( 227, 38, 54 ));
		colorTable_.put(COLOR_AMARANTH, new Color( 229, 43, 80 ));
		colorTable_.put(COLOR_AMARANTH_PINK, new Color( 241, 156, 187 ));
		colorTable_.put(COLOR_AMBER, new Color( 255, 191, 0 ));
		colorTable_.put(COLOR_AMETHYST, new Color( 153, 102, 204 ));
		colorTable_.put(COLOR_APRICOT, new Color( 251, 206, 177 ));
		colorTable_.put(COLOR_AQUA, new Color( 0, 255, 255 ));
		colorTable_.put(COLOR_AQUAMARINE, new Color( 127, 255, 212 ));
		colorTable_.put(COLOR_ARMY_GREEN, new Color( 75, 83, 32 ));
		colorTable_.put(COLOR_ASPARAGUS, new Color( 123, 160, 91 ));
		colorTable_.put(COLOR_ATOMIC_TANGERINE, new Color( 255, 153, 102 ));
		colorTable_.put(COLOR_AUBURN, new Color( 111, 53, 26 ));
		colorTable_.put(COLOR_AZURE_COLOR_WHEEL, new Color( 0, 127, 255 ));
		colorTable_.put(COLOR_AZURE_WEB, new Color( 0, 255, 255 ));
		colorTable_.put(COLOR_BABY_BLUE, new Color( 224, 255, 255 ));
		colorTable_.put(COLOR_BEIGE, new Color( 245, 245, 220 ));
		colorTable_.put(COLOR_BISTRE, new Color( 61, 43, 31 ));
		colorTable_.put(COLOR_BLACK, new Color( 0, 0, 0 ));
		colorTable_.put(COLOR_BLUE, new Color( 0, 0, 255 ));
		colorTable_.put(COLOR_BLUE_PIGMENT, new Color( 51, 51, 153 ));
		colorTable_.put(COLOR_BLUE_RYB, new Color( 2, 71, 254 ));
		colorTable_.put(COLOR_BLUE_GREEN, new Color( 0, 223, 223 ));
		colorTable_.put(COLOR_BLUE_VIOLET, new Color( 138, 43, 226 ));
		colorTable_.put(COLOR_BONDI_BLUE, new Color( 0, 149, 182 ));
		colorTable_.put(COLOR_BRASS, new Color( 181, 166, 66 ));
		colorTable_.put(COLOR_BRIGHT_GREEN, new Color( 102, 255, 0 ));
		colorTable_.put(COLOR_BRIGHT_PINK, new Color( 255, 0, 127 ));
		colorTable_.put(COLOR_BRIGHT_TURQUOISE, new Color( 8, 232, 222 ));
		colorTable_.put(COLOR_BRILLIANT_ROSE, new Color( 255, 85, 163 ));
		colorTable_.put(COLOR_BRINK_PINK, new Color( 251, 96, 127 ));
		colorTable_.put(COLOR_BRONZE, new Color( 205, 127, 50 ));
		colorTable_.put(COLOR_BROWN, new Color( 150, 75, 0 ));
		colorTable_.put(COLOR_BUFF, new Color( 240, 220, 130 ));
		colorTable_.put(COLOR_BURGUNDY, new Color( 128, 0, 32 ));
		colorTable_.put(COLOR_BURNT_ORANGE, new Color( 204, 85, 0 ));
		colorTable_.put(COLOR_BURNT_SIENNA, new Color( 233, 116, 81 ));
		colorTable_.put(COLOR_BURNT_UMBER, new Color( 138, 51, 36 ));
		colorTable_.put(COLOR_CAMOUFLAGE_GREEN, new Color( 120, 134, 107 ));
		colorTable_.put(COLOR_CAPUT_MORTUUM, new Color( 89, 39, 32 ));
		colorTable_.put(COLOR_CARDINAL, new Color( 196, 30, 58 ));
		colorTable_.put(COLOR_CARMINE, new Color( 150, 0, 24 ));
		colorTable_.put(COLOR_CARMINE_PINK, new Color( 235, 76, 66 ));
		colorTable_.put(COLOR_CARNATION_PINK, new Color( 255, 166, 201 ));
		colorTable_.put(COLOR_CAROLINA_BLUE, new Color( 156, 186, 227 ));
		colorTable_.put(COLOR_CARROT_ORANGE, new Color( 237, 145, 33 ));
		colorTable_.put(COLOR_CELADON, new Color( 172, 225, 175 ));
		colorTable_.put(COLOR_CERISE, new Color( 222, 49, 99 ));
		colorTable_.put(COLOR_CERISE_PINK, new Color( 236, 59, 131 ));
		colorTable_.put(COLOR_CERULEAN, new Color( 0, 123, 167 ));
		colorTable_.put(COLOR_CERULEAN_BLUE, new Color( 42, 82, 190 ));
		colorTable_.put(COLOR_CHAMPAGNE, new Color( 247, 231, 206 ));
		colorTable_.put(COLOR_CHARCOAL, new Color( 70, 70, 70 ));
		colorTable_.put(COLOR_CHARTREUSE_TRADITIONAL, new Color( 223, 255, 0 ));
		colorTable_.put(COLOR_CHARTREUSE_WEB, new Color( 127, 255, 0 ));
		colorTable_.put(COLOR_CHERRY_BLOSSOM_PINK, new Color( 255, 183, 197 ));
		colorTable_.put(COLOR_CHESTNUT, new Color( 205, 92, 92 ));
		colorTable_.put(COLOR_CHOCOLATE, new Color( 123, 63, 0 ));
		colorTable_.put(COLOR_CINNABAR, new Color( 227, 66, 52 ));
		colorTable_.put(COLOR_CINNAMON, new Color( 210, 105, 30 ));
		colorTable_.put(COLOR_COBALT, new Color( 0, 71, 171 ));
		colorTable_.put(COLOR_COLUMBIA_BLUE, new Color( 155, 221, 255 ));
		colorTable_.put(COLOR_COPPER, new Color( 184, 115, 51 ));
		colorTable_.put(COLOR_COPPER_ROSE, new Color( 153, 102, 102 ));
		colorTable_.put(COLOR_CORAL, new Color( 255, 127, 80 ));
		colorTable_.put(COLOR_CORAL_PINK, new Color( 248, 131, 121 ));
		colorTable_.put(COLOR_CORAL_RED, new Color( 255, 64, 64 ));
		colorTable_.put(COLOR_CORN, new Color( 251, 236, 93 ));
		colorTable_.put(COLOR_CORNFLOWER_BLUE, new Color( 100, 149, 237 ));
		colorTable_.put(COLOR_COSMIC_LATTE, new Color( 255, 248, 231 ));
		colorTable_.put(COLOR_CREAM, new Color( 255, 253, 208 ));
		colorTable_.put(COLOR_CRIMSON, new Color( 220, 20, 60 ));
		colorTable_.put(COLOR_CYAN, new Color( 0, 255, 255 ));
		colorTable_.put(COLOR_CYAN_PROCESS, new Color( 0, 180, 247 ));
		colorTable_.put(COLOR_DARK_BLUE, new Color( 0, 0, 139 ));
		colorTable_.put(COLOR_DARK_BROWN, new Color( 101, 67, 33 ));
		colorTable_.put(COLOR_DARK_CERULEAN, new Color( 8, 69, 126 ));
		colorTable_.put(COLOR_DARK_CHESTNUT, new Color( 152, 105, 96 ));
		colorTable_.put(COLOR_DARK_CORAL, new Color( 205, 91, 69 ));
		colorTable_.put(COLOR_DARK_GOLDENROD, new Color( 184, 134, 11 ));
		colorTable_.put(COLOR_DARK_GREEN, new Color( 1, 50, 32 ));
		colorTable_.put(COLOR_DARK_KHAKI, new Color( 189, 183, 107 ));
		colorTable_.put(COLOR_DARK_MAGENTA, new Color( 139, 0, 139 ));
		colorTable_.put(COLOR_DARK_PASTEL_GREEN, new Color( 3, 192, 60 ));
		colorTable_.put(COLOR_DARK_PINK, new Color( 231, 84, 128 ));
		colorTable_.put(COLOR_DARK_SCARLET, new Color( 86, 3, 25 ));
		colorTable_.put(COLOR_DARK_SALMON, new Color( 233, 150, 122 ));
		colorTable_.put(COLOR_DARK_SLATE_GRAY, new Color( 47, 79, 79 ));
		colorTable_.put(COLOR_DARK_SPRING_GREEN, new Color( 23, 114, 69 ));
		colorTable_.put(COLOR_DARK_TAN, new Color( 145, 129, 81 ));
		colorTable_.put(COLOR_DARK_TURQUOISE, new Color( 0, 206, 209 ));
		colorTable_.put(COLOR_DARK_VIOLET, new Color( 148, 0, 211 ));
		colorTable_.put(COLOR_DEEP_CARMINE_PINK, new Color( 239, 48, 56 ));
		colorTable_.put(COLOR_DEEP_CERISE, new Color( 218, 50, 135 ));
		colorTable_.put(COLOR_DEEP_CHESTNUT, new Color( 185, 78, 72 ));
		colorTable_.put(COLOR_DEEP_FUCHSIA, new Color( 193, 84, 193 ));
		colorTable_.put(COLOR_DEEP_LILAC, new Color( 153, 85, 187 ));
		colorTable_.put(COLOR_DEEP_MAGENTA, new Color( 204, 0, 204 ));
		colorTable_.put(COLOR_DEEP_PEACH, new Color( 255, 203, 164 ));
		colorTable_.put(COLOR_DEEP_PINK, new Color( 255, 20, 147 ));
		colorTable_.put(COLOR_DENIM, new Color( 21, 96, 189 ));
		colorTable_.put(COLOR_DODGER_BLUE, new Color( 30, 144, 255 ));
		colorTable_.put(COLOR_ECRU, new Color( 194, 178, 128 ));
		colorTable_.put(COLOR_EGYPTIAN_BLUE, new Color( 16, 52, 166 ));
		colorTable_.put(COLOR_ELECTRIC_BLUE, new Color( 125, 249, 255 ));
		colorTable_.put(COLOR_ELECTRIC_GREEN_X11_GREEN, new Color( 0, 255, 0 ));
		colorTable_.put(COLOR_ELECTRIC_INDIGO, new Color( 102, 0, 255 ));
		colorTable_.put(COLOR_ELECTRIC_LIME, new Color( 204, 255, 0 ));
		colorTable_.put(COLOR_ELECTRIC_PURPLE, new Color( 191, 0, 255 ));
		colorTable_.put(COLOR_EMERALD, new Color( 80, 200, 120 ));
		colorTable_.put(COLOR_EGGPLANT, new Color( 97, 64, 81 ));
		colorTable_.put(COLOR_FALU_RED, new Color( 128, 24, 24 ));
		colorTable_.put(COLOR_FERN_GREEN, new Color( 79, 121, 66 ));
		colorTable_.put(COLOR_FIREBRICK, new Color( 178, 34, 34 ));
		colorTable_.put(COLOR_FLAX, new Color( 238, 220, 130 ));
		colorTable_.put(COLOR_FOREST_GREEN, new Color( 34, 139, 34 ));
		colorTable_.put(COLOR_FRENCH_ROSE, new Color( 246, 74, 138 ));
		colorTable_.put(COLOR_FUCHSIA, new Color( 255, 0, 255 ));
		colorTable_.put(COLOR_FUCHSIA_PINK, new Color( 255, 119, 255 ));
		colorTable_.put(COLOR_GAMBOGE, new Color( 228, 155, 15 ));
		colorTable_.put(COLOR_GOLD_METALLIC, new Color( 212, 175, 55 ));
		colorTable_.put(COLOR_GOLD_WEB_GOLDEN, new Color( 255, 215, 0 ));
		colorTable_.put(COLOR_GOLDEN_BROWN, new Color( 153, 101, 21 ));
		colorTable_.put(COLOR_GOLDEN_YELLOW, new Color( 255, 223, 0 ));
		colorTable_.put(COLOR_GOLDENROD, new Color( 218, 165, 32 ));
		colorTable_.put(COLOR_GREY_ASPARAGUS, new Color( 70, 89, 69 ));
		colorTable_.put(COLOR_GREEN_COLOR_WHEEL_X11_GREEN, new Color( 0, 255, 0 ));
		colorTable_.put(COLOR_GREEN_HTMLCSS_GREEN, new Color( 0, 128, 0 ));
		colorTable_.put(COLOR_GREEN_PIGMENT, new Color( 0, 165, 80 ));
		colorTable_.put(COLOR_GREEN_RYB, new Color( 102, 176, 50 ));
		colorTable_.put(COLOR_GREEN_YELLOW, new Color( 173, 255, 47 ));
		colorTable_.put(COLOR_GREY, new Color( 128, 128, 128 ));
		colorTable_.put(COLOR_HAN_PURPLE, new Color( 82, 24, 250 ));
		colorTable_.put(COLOR_HARLEQUIN, new Color( 63, 255, 0 ));
		colorTable_.put(COLOR_HELIOTROPE, new Color( 223, 115, 255 ));
		colorTable_.put(COLOR_HOLLYWOOD_CERISE, new Color( 244, 0, 161 ));
		colorTable_.put(COLOR_HOT_MAGENTA, new Color( 255, 0, 204 ));
		colorTable_.put(COLOR_HOT_PINK, new Color( 255, 105, 180 ));
		colorTable_.put(COLOR_INDIGO_DYE, new Color( 0, 65, 106 ));
		colorTable_.put(COLOR_INDIGO_WEB, new Color( 75, 0, 130 ));
		colorTable_.put(COLOR_INTERNATIONAL_KLEIN_BLUE, new Color( 0, 47, 167 ));
		colorTable_.put(COLOR_INTERNATIONAL_ORANGE, new Color( 255, 79, 0 ));
		colorTable_.put(COLOR_ISLAMIC_GREEN, new Color( 0, 153, 0 ));
		colorTable_.put(COLOR_IVORY, new Color( 255, 255, 240 ));
		colorTable_.put(COLOR_JADE, new Color( 0, 168, 107 ));
		colorTable_.put(COLOR_KELLY_GREEN, new Color( 76, 187, 23 ));
		colorTable_.put(COLOR_KHAKI, new Color( 195, 176, 145 ));
		colorTable_.put(COLOR_KHAKI_X11_LIGHT_KHAKI, new Color( 240, 230, 140 ));
		colorTable_.put(COLOR_LAVENDER_FLORAL, new Color( 181, 126, 220 ));
		colorTable_.put(COLOR_LAVENDER_WEB, new Color( 230, 230, 250 ));
		colorTable_.put(COLOR_LAVENDER_BLUE, new Color( 204, 204, 255 ));
		colorTable_.put(COLOR_LAVENDER_BLUSH, new Color( 255, 240, 245 ));
		colorTable_.put(COLOR_LAVENDER_GREY, new Color( 196, 195, 221 ));
		colorTable_.put(COLOR_LAVENDER_MAGENTA, new Color( 238, 130, 238 ));
		colorTable_.put(COLOR_LAVENDER_PINK, new Color( 251, 174, 210 ));
		colorTable_.put(COLOR_LAVENDER_PURPLE, new Color( 150, 120, 182 ));
		colorTable_.put(COLOR_LAVENDER_ROSE, new Color( 251, 160, 227 ));
		colorTable_.put(COLOR_LAWN_GREEN, new Color( 124, 252, 0 ));
		colorTable_.put(COLOR_LEMON, new Color( 253, 233, 16 ));
		colorTable_.put(COLOR_LEMON_CHIFFON, new Color( 255, 250, 205 ));
		colorTable_.put(COLOR_LIGHT_BLUE, new Color( 173, 216, 230 ));
		colorTable_.put(COLOR_LIGHT_PINK, new Color( 255, 182, 193 ));
		colorTable_.put(COLOR_LILAC, new Color( 200, 162, 200 ));
		colorTable_.put(COLOR_LIME_COLOR_WHEEL, new Color( 191, 255, 0 ));
		colorTable_.put(COLOR_LIME_WEB_X11_GREEN, new Color( 0, 255, 0 ));
		colorTable_.put(COLOR_LIME_GREEN, new Color( 50, 205, 50 ));
		colorTable_.put(COLOR_LINEN, new Color( 250, 240, 230 ));
		colorTable_.put(COLOR_MAGENTA, new Color( 255, 0, 255 ));
		colorTable_.put(COLOR_MAGENTA_DYE, new Color( 202, 31, 23 ));
		colorTable_.put(COLOR_MAGENTA_PROCESS, new Color( 255, 0, 144 ));
		colorTable_.put(COLOR_MAGIC_MINT, new Color( 170, 240, 209 ));
		colorTable_.put(COLOR_MAGNOLIA, new Color( 248, 244, 255 ));
		colorTable_.put(COLOR_MALACHITE, new Color( 11, 218, 81 ));
		colorTable_.put(COLOR_MAROON_HTMLCSS, new Color( 128, 0, 0 ));
		colorTable_.put(COLOR_MAROON_X11, new Color( 176, 48, 96 ));
		colorTable_.put(COLOR_MAYA_BLUE, new Color( 115, 194, 251 ));
		colorTable_.put(COLOR_MAUVE, new Color( 224, 176, 255 ));
		colorTable_.put(COLOR_MAUVE_TAUPE, new Color( 145, 95, 109 ));
		colorTable_.put(COLOR_MEDIUM_BLUE, new Color( 0, 0, 205 ));
		colorTable_.put(COLOR_MEDIUM_CARMINE, new Color( 175, 64, 53 ));
		colorTable_.put(COLOR_MEDIUM_LAVENDER_MAGENTA, new Color( 204, 153, 204 ));
		colorTable_.put(COLOR_MEDIUM_PURPLE, new Color( 147, 112, 219 ));
		colorTable_.put(COLOR_MEDIUM_SPRING_GREEN, new Color( 0, 250, 154 ));
		colorTable_.put(COLOR_MIDNIGHT_BLUE, new Color( 25, 25, 112 ));
		colorTable_.put(COLOR_MIDNIGHT_GREEN_EAGLE_GREEN, new Color( 0, 73, 83 ));
		colorTable_.put(COLOR_MINT_GREEN, new Color( 152, 255, 152 ));
		colorTable_.put(COLOR_MISTY_ROSE, new Color( 255, 228, 225 ));
		colorTable_.put(COLOR_MOSS_GREEN, new Color( 173, 223, 173 ));
		colorTable_.put(COLOR_MOUNTBATTEN_PINK, new Color( 153, 122, 141 ));
		colorTable_.put(COLOR_MUSTARD, new Color( 255, 219, 88 ));
		colorTable_.put(COLOR_MYRTLE, new Color( 33, 66, 30 ));
		colorTable_.put(COLOR_NAVAJO_WHITE, new Color( 255, 222, 173 ));
		colorTable_.put(COLOR_NAVY_BLUE, new Color( 0, 0, 128 ));
		colorTable_.put(COLOR_OCHRE, new Color( 204, 119, 34 ));
		colorTable_.put(COLOR_OFFICE_GREEN, new Color( 0, 128, 0 ));
		colorTable_.put(COLOR_OLD_GOLD, new Color( 207, 181, 59 ));
		colorTable_.put(COLOR_OLD_LACE, new Color( 253, 245, 230 ));
		colorTable_.put(COLOR_OLD_LAVENDER, new Color( 121, 104, 120 ));
		colorTable_.put(COLOR_OLD_ROSE, new Color( 192, 128, 129 ));
		colorTable_.put(COLOR_OLIVE, new Color( 128, 128, 0 ));
		colorTable_.put(COLOR_OLIVE_DRAB, new Color( 107, 142, 35 ));
		colorTable_.put(COLOR_OLIVINE, new Color( 154, 185, 115 ));
		colorTable_.put(COLOR_ORANGE_COLOR_WHEEL, new Color( 255, 127, 0 ));
		colorTable_.put(COLOR_ORANGE_RYB, new Color( 251, 153, 2 ));
		colorTable_.put(COLOR_ORANGE_WEB, new Color( 255, 165, 0 ));
		colorTable_.put(COLOR_ORANGE_PEEL, new Color( 255, 160, 0 ));	  
		colorTable_.put(COLOR_ORANGE_RED, new Color( 255, 69, 0 ));	  
		colorTable_.put(COLOR_ORCHID, new Color( 218, 112, 214 ));	  	  
		colorTable_.put(COLOR_PALE_BLUE, new Color( 175, 238, 238 ));	  
		colorTable_.put(COLOR_PALE_BROWN, new Color( 152, 118, 84 ));	  
		colorTable_.put(COLOR_PALE_CARMINE, new Color( 175, 64, 53 ));	  
		colorTable_.put(COLOR_PALE_CHESTNUT, new Color( 221, 173, 175 ));	  
		colorTable_.put(COLOR_PALE_CORNFLOWER_BLUE, new Color( 171, 205, 239 ));	  
		colorTable_.put(COLOR_PALE_MAGENTA, new Color( 249, 132, 229 ));	  
		colorTable_.put(COLOR_PALE_PINK, new Color( 250, 218, 221 ));	  
		colorTable_.put(COLOR_PALE_RED_VIOLET, new Color( 219, 112, 147 ));	  
		colorTable_.put(COLOR_PAPAYA_WHIP, new Color( 255, 239, 213 ));	  
		colorTable_.put(COLOR_PASTEL_GREEN, new Color( 119, 221, 119 ));	  
		colorTable_.put(COLOR_PASTEL_PINK, new Color( 255, 209, 220 ));	  
		colorTable_.put(COLOR_PEACH, new Color( 255, 229, 180 ));	  
		colorTable_.put(COLOR_PEACH_ORANGE, new Color( 255, 204, 153 ));	  
		colorTable_.put(COLOR_PEACH_YELLOW, new Color( 250, 223, 173 ));	  
		colorTable_.put(COLOR_PEAR, new Color( 209, 226, 49 ));	  
		colorTable_.put(COLOR_PERIWINKLE, new Color( 204, 204, 255 ));	  
		colorTable_.put(COLOR_PERSIAN_BLUE, new Color( 28, 57, 187 ));	  
		colorTable_.put(COLOR_PERSIAN_GREEN, new Color( 0, 166, 147 ));	  
		colorTable_.put(COLOR_PERSIAN_INDIGO, new Color( 50, 18, 122 ));	  
		colorTable_.put(COLOR_PERSIAN_ORANGE, new Color( 217, 144, 88 ));	  
		colorTable_.put(COLOR_PERSIAN_RED, new Color( 204, 51, 51 ));	  
		colorTable_.put(COLOR_PERSIAN_PINK, new Color( 247, 127, 190 ));	  
		colorTable_.put(COLOR_PERSIAN_ROSE, new Color( 254, 40, 162 ));	  
		colorTable_.put(COLOR_PERSIMMON, new Color( 236, 88, 0 ));	  
		colorTable_.put(COLOR_PINE_GREEN, new Color( 1, 121, 111 ));	  
		colorTable_.put(COLOR_PINK, new Color( 255, 192, 203 ));	  
		colorTable_.put(COLOR_PINK_ORANGE, new Color( 255, 153, 102 ));	  
		colorTable_.put(COLOR_PLATINUM, new Color( 229, 228, 226 ));	  
		colorTable_.put(COLOR_PLUM_WEB, new Color( 204, 153, 204 ));	  
		colorTable_.put(COLOR_POWDER_BLUE_WEB, new Color( 176, 224, 230 ));	  
		colorTable_.put(COLOR_PUCE, new Color( 204, 136, 153 ));	  
		colorTable_.put(COLOR_PRUSSIAN_BLUE, new Color( 0, 49, 83 ));	  
		colorTable_.put(COLOR_PSYCHEDELIC_PURPLE, new Color( 221, 0, 255 ));	  
		colorTable_.put(COLOR_PUMPKIN, new Color( 255, 117, 24 ));	  
		colorTable_.put(COLOR_PURPLE_HTMLCSS, new Color( 128, 0, 128 ));	  
		colorTable_.put(COLOR_PURPLE_X11, new Color( 160, 92, 240 ));	  
		colorTable_.put(COLOR_PURPLE_TAUPE, new Color( 80, 64, 77 ));	  
		colorTable_.put(COLOR_RAW_UMBER, new Color( 115, 74, 18 ));	  
		colorTable_.put(COLOR_RAZZMATAZZ, new Color( 227, 11, 92 ));	  
		colorTable_.put(COLOR_RED, new Color( 255, 0, 0 ));	  
		colorTable_.put(COLOR_RED_PIGMENT, new Color( 237, 28, 36 ));	  
		colorTable_.put(COLOR_RED_RYB, new Color( 254, 39, 18 ));	  
		colorTable_.put(COLOR_RED_VIOLET, new Color( 199, 21, 133 ));	  
		colorTable_.put(COLOR_RICH_CARMINE, new Color( 215, 0, 64 ));	  
		colorTable_.put(COLOR_ROBIN_EGG_BLUE, new Color( 0, 204, 204 ));	  
		colorTable_.put(COLOR_ROSE, new Color( 255, 0, 127 ));	  
		colorTable_.put(COLOR_ROSE_MADDER, new Color( 227, 38, 54 ));	  
		colorTable_.put(COLOR_ROSE_TAUPE, new Color( 144, 93, 93 ));	  
		colorTable_.put(COLOR_ROYAL_BLUE, new Color( 65, 105, 225 ));
		colorTable_.put(COLOR_ROYAL_PURPLE, new Color( 107, 63, 160 ));
		colorTable_.put(COLOR_RUBY, new Color( 224, 17, 95 ));
		colorTable_.put(COLOR_RUSSET, new Color( 128, 70, 27 ));	  
		colorTable_.put(COLOR_RUST, new Color( 183, 65, 14 ));	  
		colorTable_.put(COLOR_SAFETY_ORANGE_BLAZE_ORANGE, new Color( 255, 102, 0 ));	  
		colorTable_.put(COLOR_SAFFRON, new Color( 244, 196, 48 ));	  
		colorTable_.put(COLOR_SALMON, new Color( 255, 140, 105 ));	  
		colorTable_.put(COLOR_SANDY_BROWN, new Color( 244, 164, 96 ));	  
		colorTable_.put(COLOR_SANGRIA, new Color( 146, 0, 10 ));	  
		colorTable_.put(COLOR_SAPPHIRE, new Color( 8, 37, 103 ));	  
		colorTable_.put(COLOR_SCARLET, new Color( 255, 36, 0 ));	  
		colorTable_.put(COLOR_SCHOOL_BUS_YELLOW, new Color( 255, 216, 0 ));	  
		colorTable_.put(COLOR_SEA_GREEN, new Color( 46, 139, 87 ));	  
		colorTable_.put(COLOR_SEASHELL, new Color( 255, 245, 238 ));	  
		colorTable_.put(COLOR_SELECTIVE_YELLOW, new Color( 255, 186, 0 ));	  
		colorTable_.put(COLOR_SEPIA, new Color( 112, 66, 20 ));	  
		colorTable_.put(COLOR_SHAMROCK_GREEN, new Color( 0, 158, 96 ));	  
		colorTable_.put(COLOR_SHOCKING_PINK, new Color( 252, 15, 192 ));	  
		colorTable_.put(COLOR_SILVER, new Color( 192, 192, 192 ));	  
		colorTable_.put(COLOR_SKY_BLUE, new Color( 135, 206, 235 ));	  
		colorTable_.put(COLOR_SLATE_GREY, new Color( 112, 128, 144 ));	  
		colorTable_.put(COLOR_SMALT_DARK_POWDER_BLUE, new Color( 0, 51, 153 ));	  
		colorTable_.put(COLOR_SPRING_BUD, new Color( 167, 252, 0 ));	  
		colorTable_.put(COLOR_SPRING_GREEN, new Color( 0, 255, 127 ));	  
		colorTable_.put(COLOR_STEEL_BLUE, new Color( 70, 130, 180 ));	  
		colorTable_.put(COLOR_TAN, new Color( 210, 180, 140 ));	  
		colorTable_.put(COLOR_TANGERINE, new Color( 242, 133, 0 ));	  
		colorTable_.put(COLOR_TANGERINE_YELLOW, new Color( 255, 204, 0 ));	  
		colorTable_.put(COLOR_TAUPE, new Color( 72, 60, 50 ));	  
		colorTable_.put(COLOR_TEA_GREEN, new Color( 208, 240, 192 ));	  
		colorTable_.put(COLOR_TEA_ROSE_ORANGE, new Color( 248, 131, 194 ));	  
		colorTable_.put(COLOR_TEA_ROSE_ROSE, new Color( 244, 194, 194 ));	  
		colorTable_.put(COLOR_TEAL, new Color( 0, 128, 128 ));	  
		colorTable_.put(COLOR_TENNY_TAWNY, new Color( 205, 87, 0 ));	  
		colorTable_.put(COLOR_TERRA_COTTA, new Color( 226, 114, 91 ));	  
		colorTable_.put(COLOR_THISTLE, new Color( 216, 191, 216 ));	  
		colorTable_.put(COLOR_TOMATO, new Color( 255, 99, 71 ));	  
		colorTable_.put(COLOR_TURQUOISE, new Color( 48, 213, 200 ));	  
		colorTable_.put(COLOR_TYRIAN_PURPLE, new Color( 102, 2, 60 ));	  
		colorTable_.put(COLOR_ULTRAMARINE, new Color( 18 , 10 , 143 ));	  
		colorTable_.put(COLOR_ULTRA_PINK, new Color( 255, 111, 255 ));	  
		colorTable_.put(COLOR_UNITED_NATIONS_BLUE, new Color( 91, 146, 229 ));	  
		colorTable_.put(COLOR_VEGAS_GOLD, new Color( 197, 179, 88 ));	  
		colorTable_.put(COLOR_VERMILION, new Color( 227, 66, 51 ));	  
		colorTable_.put(COLOR_VIOLET, new Color( 139, 0, 255 ));	  
		colorTable_.put(COLOR_VIOLET_WEB, new Color( 238, 130, 238 ));	  
		colorTable_.put(COLOR_VIOLET_RYB, new Color( 2, 71, 54 ));	  
		colorTable_.put(COLOR_VIRIDIAN, new Color( 64, 130, 109 ));	  
		colorTable_.put(COLOR_WHEAT, new Color( 245, 222, 179 ));
		colorTable_.put(COLOR_WHITE, new Color( 255, 255, 255 ));
		colorTable_.put(COLOR_WISTERIA, new Color( 201, 160, 220 ));
		colorTable_.put(COLOR_YELLOW, new Color( 255, 255, 0 ));
		colorTable_.put(COLOR_YELLOW_PROCESS, new Color( 255, 239, 0 ));
		colorTable_.put(COLOR_YELLOW_RYB, new Color( 254, 254, 51 ));
		colorTable_.put(COLOR_YELLOW_GREEN, new Color( 154, 205, 50 ));
	}
	
	public static Hashtable<String, Color> getColorTable() {
		return colorTable_;
	}
	
	public static boolean containsColor(String colorURI) {
		return colorTable_.containsKey(colorURI);
	}
	
	public static Color getRGBforColor(String colorURI) {
		return colorTable_.get(colorURI);
	}

	// A.2.2	LocationCS
	private static Vector<String> locationTable_ = new Vector<String>();
	
	public final static String LOCATIONURIBASE = "urn:mpeg:mpeg-v:01-SI-LocationCS-NS";
	public final static String LOC_X_LEFT = ":left";
	public final static String LOC_X_CENTERLEFT = ":centerleft";
	public final static String LOC_X_CENTER = ":center";
	public final static String LOC_X_CENTERRIGHT = ":centerright";
	public final static String LOC_X_RIGHT = ":right";
	public final static String LOC_Y_BOTTOM = ":bottom";
	public final static String LOC_Y_MIDDLE = ":middle";
	public final static String LOC_Y_TOP = ":top";
	public final static String LOC_Z_BACK = ":back";
	public final static String LOC_Z_MIDWAY = ":midway";
	public final static String LOC_Z_FRONT = ":front";
	public final static String LOC_EVERYWHERE = ":*";
	
	static {
		// x left
		locationTable_.add(LOCATIONURIBASE + LOC_X_LEFT + LOC_Y_BOTTOM + LOC_Z_BACK);
		locationTable_.add(LOCATIONURIBASE + LOC_X_LEFT + LOC_Y_BOTTOM + LOC_Z_MIDWAY);
		locationTable_.add(LOCATIONURIBASE + LOC_X_LEFT + LOC_Y_BOTTOM + LOC_Z_FRONT);
		locationTable_.add(LOCATIONURIBASE + LOC_X_LEFT + LOC_Y_MIDDLE + LOC_Z_BACK);
		locationTable_.add(LOCATIONURIBASE + LOC_X_LEFT + LOC_Y_MIDDLE + LOC_Z_MIDWAY);
		locationTable_.add(LOCATIONURIBASE + LOC_X_LEFT + LOC_Y_MIDDLE + LOC_Z_FRONT);
		locationTable_.add(LOCATIONURIBASE + LOC_X_LEFT + LOC_Y_TOP + LOC_Z_BACK);
		locationTable_.add(LOCATIONURIBASE + LOC_X_LEFT + LOC_Y_TOP + LOC_Z_MIDWAY);
		locationTable_.add(LOCATIONURIBASE + LOC_X_LEFT + LOC_Y_TOP + LOC_Z_FRONT);
		locationTable_.add(LOCATIONURIBASE + LOC_X_LEFT + LOC_EVERYWHERE + LOC_Z_BACK);
		locationTable_.add(LOCATIONURIBASE + LOC_X_LEFT + LOC_EVERYWHERE + LOC_Z_MIDWAY);
		locationTable_.add(LOCATIONURIBASE + LOC_X_LEFT + LOC_EVERYWHERE + LOC_Z_FRONT);
		locationTable_.add(LOCATIONURIBASE + LOC_X_LEFT + LOC_Y_BOTTOM + LOC_EVERYWHERE);
		locationTable_.add(LOCATIONURIBASE + LOC_X_LEFT + LOC_Y_MIDDLE + LOC_EVERYWHERE);
		locationTable_.add(LOCATIONURIBASE + LOC_X_LEFT + LOC_Y_TOP + LOC_EVERYWHERE);
		locationTable_.add(LOCATIONURIBASE + LOC_X_LEFT + LOC_EVERYWHERE + LOC_EVERYWHERE);
		
		// x centerleft
		locationTable_.add(LOCATIONURIBASE + LOC_X_CENTERLEFT + LOC_Y_BOTTOM + LOC_Z_BACK);
		locationTable_.add(LOCATIONURIBASE + LOC_X_CENTERLEFT + LOC_Y_BOTTOM + LOC_Z_MIDWAY);
		locationTable_.add(LOCATIONURIBASE + LOC_X_CENTERLEFT + LOC_Y_BOTTOM + LOC_Z_FRONT);
		locationTable_.add(LOCATIONURIBASE + LOC_X_CENTERLEFT + LOC_Y_MIDDLE + LOC_Z_BACK);
		locationTable_.add(LOCATIONURIBASE + LOC_X_CENTERLEFT + LOC_Y_MIDDLE + LOC_Z_MIDWAY);
		locationTable_.add(LOCATIONURIBASE + LOC_X_CENTERLEFT + LOC_Y_MIDDLE + LOC_Z_FRONT);
		locationTable_.add(LOCATIONURIBASE + LOC_X_CENTERLEFT + LOC_Y_TOP + LOC_Z_BACK);
		locationTable_.add(LOCATIONURIBASE + LOC_X_CENTERLEFT + LOC_Y_TOP + LOC_Z_MIDWAY);
		locationTable_.add(LOCATIONURIBASE + LOC_X_CENTERLEFT + LOC_Y_TOP + LOC_Z_FRONT);
		locationTable_.add(LOCATIONURIBASE + LOC_X_CENTERLEFT + LOC_EVERYWHERE + LOC_Z_BACK);
		locationTable_.add(LOCATIONURIBASE + LOC_X_CENTERLEFT + LOC_EVERYWHERE + LOC_Z_MIDWAY);
		locationTable_.add(LOCATIONURIBASE + LOC_X_CENTERLEFT + LOC_EVERYWHERE + LOC_Z_FRONT);
		locationTable_.add(LOCATIONURIBASE + LOC_X_CENTERLEFT + LOC_Y_BOTTOM + LOC_EVERYWHERE);
		locationTable_.add(LOCATIONURIBASE + LOC_X_CENTERLEFT + LOC_Y_MIDDLE + LOC_EVERYWHERE);
		locationTable_.add(LOCATIONURIBASE + LOC_X_CENTERLEFT + LOC_Y_TOP + LOC_EVERYWHERE);
		locationTable_.add(LOCATIONURIBASE + LOC_X_CENTERLEFT + LOC_EVERYWHERE + LOC_EVERYWHERE);

		// x center
		locationTable_.add(LOCATIONURIBASE + LOC_X_CENTER + LOC_Y_BOTTOM + LOC_Z_BACK);
		locationTable_.add(LOCATIONURIBASE + LOC_X_CENTER + LOC_Y_BOTTOM + LOC_Z_MIDWAY);
		locationTable_.add(LOCATIONURIBASE + LOC_X_CENTER + LOC_Y_BOTTOM + LOC_Z_FRONT);
		locationTable_.add(LOCATIONURIBASE + LOC_X_CENTER + LOC_Y_MIDDLE + LOC_Z_BACK);
		locationTable_.add(LOCATIONURIBASE + LOC_X_CENTER + LOC_Y_MIDDLE + LOC_Z_MIDWAY);
		locationTable_.add(LOCATIONURIBASE + LOC_X_CENTER + LOC_Y_MIDDLE + LOC_Z_FRONT);
		locationTable_.add(LOCATIONURIBASE + LOC_X_CENTER + LOC_Y_TOP + LOC_Z_BACK);
		locationTable_.add(LOCATIONURIBASE + LOC_X_CENTER + LOC_Y_TOP + LOC_Z_MIDWAY);
		locationTable_.add(LOCATIONURIBASE + LOC_X_CENTER + LOC_Y_TOP + LOC_Z_FRONT);
		locationTable_.add(LOCATIONURIBASE + LOC_X_CENTER + LOC_EVERYWHERE + LOC_Z_BACK);
		locationTable_.add(LOCATIONURIBASE + LOC_X_CENTER + LOC_EVERYWHERE + LOC_Z_MIDWAY);
		locationTable_.add(LOCATIONURIBASE + LOC_X_CENTER + LOC_EVERYWHERE + LOC_Z_FRONT);
		locationTable_.add(LOCATIONURIBASE + LOC_X_CENTER + LOC_Y_BOTTOM + LOC_EVERYWHERE);
		locationTable_.add(LOCATIONURIBASE + LOC_X_CENTER + LOC_Y_MIDDLE + LOC_EVERYWHERE);
		locationTable_.add(LOCATIONURIBASE + LOC_X_CENTER + LOC_Y_TOP + LOC_EVERYWHERE);
		locationTable_.add(LOCATIONURIBASE + LOC_X_CENTER + LOC_EVERYWHERE + LOC_EVERYWHERE);
		
		// x centerright
		locationTable_.add(LOCATIONURIBASE + LOC_X_CENTERRIGHT + LOC_Y_BOTTOM + LOC_Z_BACK);
		locationTable_.add(LOCATIONURIBASE + LOC_X_CENTERRIGHT + LOC_Y_BOTTOM + LOC_Z_MIDWAY);
		locationTable_.add(LOCATIONURIBASE + LOC_X_CENTERRIGHT + LOC_Y_BOTTOM + LOC_Z_FRONT);
		locationTable_.add(LOCATIONURIBASE + LOC_X_CENTERRIGHT + LOC_Y_MIDDLE + LOC_Z_BACK);
		locationTable_.add(LOCATIONURIBASE + LOC_X_CENTERRIGHT + LOC_Y_MIDDLE + LOC_Z_MIDWAY);
		locationTable_.add(LOCATIONURIBASE + LOC_X_CENTERRIGHT + LOC_Y_MIDDLE + LOC_Z_FRONT);
		locationTable_.add(LOCATIONURIBASE + LOC_X_CENTERRIGHT + LOC_Y_TOP + LOC_Z_BACK);
		locationTable_.add(LOCATIONURIBASE + LOC_X_CENTERRIGHT + LOC_Y_TOP + LOC_Z_MIDWAY);
		locationTable_.add(LOCATIONURIBASE + LOC_X_CENTERRIGHT + LOC_Y_TOP + LOC_Z_FRONT);
		locationTable_.add(LOCATIONURIBASE + LOC_X_CENTERRIGHT + LOC_EVERYWHERE + LOC_Z_BACK);
		locationTable_.add(LOCATIONURIBASE + LOC_X_CENTERRIGHT + LOC_EVERYWHERE + LOC_Z_MIDWAY);
		locationTable_.add(LOCATIONURIBASE + LOC_X_CENTERRIGHT + LOC_EVERYWHERE + LOC_Z_FRONT);
		locationTable_.add(LOCATIONURIBASE + LOC_X_CENTERRIGHT + LOC_Y_BOTTOM + LOC_EVERYWHERE);
		locationTable_.add(LOCATIONURIBASE + LOC_X_CENTERRIGHT + LOC_Y_MIDDLE + LOC_EVERYWHERE);
		locationTable_.add(LOCATIONURIBASE + LOC_X_CENTERRIGHT + LOC_Y_TOP + LOC_EVERYWHERE);
		locationTable_.add(LOCATIONURIBASE + LOC_X_CENTERRIGHT + LOC_EVERYWHERE + LOC_EVERYWHERE);
		
		// x right
		locationTable_.add(LOCATIONURIBASE + LOC_X_RIGHT + LOC_Y_BOTTOM + LOC_Z_BACK);
		locationTable_.add(LOCATIONURIBASE + LOC_X_RIGHT + LOC_Y_BOTTOM + LOC_Z_MIDWAY);
		locationTable_.add(LOCATIONURIBASE + LOC_X_RIGHT + LOC_Y_BOTTOM + LOC_Z_FRONT);
		locationTable_.add(LOCATIONURIBASE + LOC_X_RIGHT + LOC_Y_MIDDLE + LOC_Z_BACK);
		locationTable_.add(LOCATIONURIBASE + LOC_X_RIGHT + LOC_Y_MIDDLE + LOC_Z_MIDWAY);
		locationTable_.add(LOCATIONURIBASE + LOC_X_RIGHT + LOC_Y_MIDDLE + LOC_Z_FRONT);
		locationTable_.add(LOCATIONURIBASE + LOC_X_RIGHT + LOC_Y_TOP + LOC_Z_BACK);
		locationTable_.add(LOCATIONURIBASE + LOC_X_RIGHT + LOC_Y_TOP + LOC_Z_MIDWAY);
		locationTable_.add(LOCATIONURIBASE + LOC_X_RIGHT + LOC_Y_TOP + LOC_Z_FRONT);
		locationTable_.add(LOCATIONURIBASE + LOC_X_RIGHT + LOC_EVERYWHERE + LOC_Z_BACK);
		locationTable_.add(LOCATIONURIBASE + LOC_X_RIGHT + LOC_EVERYWHERE + LOC_Z_MIDWAY);
		locationTable_.add(LOCATIONURIBASE + LOC_X_RIGHT + LOC_EVERYWHERE + LOC_Z_FRONT);
		locationTable_.add(LOCATIONURIBASE + LOC_X_RIGHT + LOC_Y_BOTTOM + LOC_EVERYWHERE);
		locationTable_.add(LOCATIONURIBASE + LOC_X_RIGHT + LOC_Y_MIDDLE + LOC_EVERYWHERE);
		locationTable_.add(LOCATIONURIBASE + LOC_X_RIGHT + LOC_Y_TOP + LOC_EVERYWHERE);
		locationTable_.add(LOCATIONURIBASE + LOC_X_RIGHT + LOC_EVERYWHERE + LOC_EVERYWHERE);
		
		// x everywhere
		locationTable_.add(LOCATIONURIBASE + LOC_EVERYWHERE + LOC_Y_BOTTOM + LOC_Z_BACK);
		locationTable_.add(LOCATIONURIBASE + LOC_EVERYWHERE + LOC_Y_BOTTOM + LOC_Z_MIDWAY);
		locationTable_.add(LOCATIONURIBASE + LOC_EVERYWHERE + LOC_Y_BOTTOM + LOC_Z_FRONT);
		locationTable_.add(LOCATIONURIBASE + LOC_EVERYWHERE + LOC_Y_MIDDLE + LOC_Z_BACK);
		locationTable_.add(LOCATIONURIBASE + LOC_EVERYWHERE + LOC_Y_MIDDLE + LOC_Z_MIDWAY);
		locationTable_.add(LOCATIONURIBASE + LOC_EVERYWHERE + LOC_Y_MIDDLE + LOC_Z_FRONT);
		locationTable_.add(LOCATIONURIBASE + LOC_EVERYWHERE + LOC_Y_TOP + LOC_Z_BACK);
		locationTable_.add(LOCATIONURIBASE + LOC_EVERYWHERE + LOC_Y_TOP + LOC_Z_MIDWAY);
		locationTable_.add(LOCATIONURIBASE + LOC_EVERYWHERE + LOC_Y_TOP + LOC_Z_FRONT);
		locationTable_.add(LOCATIONURIBASE + LOC_EVERYWHERE + LOC_EVERYWHERE + LOC_Z_BACK);
		locationTable_.add(LOCATIONURIBASE + LOC_EVERYWHERE + LOC_EVERYWHERE + LOC_Z_MIDWAY);
		locationTable_.add(LOCATIONURIBASE + LOC_EVERYWHERE + LOC_EVERYWHERE + LOC_Z_FRONT);
		locationTable_.add(LOCATIONURIBASE + LOC_EVERYWHERE + LOC_Y_BOTTOM + LOC_EVERYWHERE);
		locationTable_.add(LOCATIONURIBASE + LOC_EVERYWHERE + LOC_Y_MIDDLE + LOC_EVERYWHERE);
		locationTable_.add(LOCATIONURIBASE + LOC_EVERYWHERE + LOC_Y_TOP + LOC_EVERYWHERE);
		locationTable_.add(LOCATIONURIBASE + LOC_EVERYWHERE + LOC_EVERYWHERE + LOC_EVERYWHERE);
	}
	
	public static Vector<String> getLocationTable() {
		return locationTable_;
	}
	
	public static boolean containsLocation(String locationURI) {
		return locationTable_.contains(locationURI);
	}

	// A.2.3	ScentCS
	private static Vector<String> scentTable_ =  new Vector<String>();
	public final static String SCENTURIBASE = "urn:mpeg:mpeg-v:01-SI-ScentCS-NS";
	public final static String SCENT_ROSE = SCENTURIBASE + ":rose";
	public final static String SCENT_ACACIA = SCENTURIBASE + ":acacia";
	public final static String SCENT_CHRYSANTHEMUM = SCENTURIBASE + ":chrysanthemum";
	public final static String SCENT_LILAC = SCENTURIBASE + ":lilac";
	public final static String SCENT_MINT = SCENTURIBASE + ":mint";
	public final static String SCENT_JASMINE = SCENTURIBASE + ":jasmine";
	public final static String SCENT_PINETREE = SCENTURIBASE + ":pine_tree";
	public final static String SCENT_ORANGE = SCENTURIBASE + ":orange";
	public final static String SCENT_GRAPE = SCENTURIBASE + ":grape";
	
	static {
		scentTable_.add(SCENT_ROSE);
		scentTable_.add(SCENT_ACACIA);
		scentTable_.add(SCENT_CHRYSANTHEMUM);
		scentTable_.add(SCENT_LILAC);
		scentTable_.add(SCENT_MINT);
		scentTable_.add(SCENT_JASMINE);
		scentTable_.add(SCENT_PINETREE);
		scentTable_.add(SCENT_ORANGE);
		scentTable_.add(SCENT_GRAPE);
	}
	
	public static Vector<String> getScentTable() {
		return scentTable_;
	}
	
	public static boolean containsScent(String scentURI) {
		return scentTable_.contains(scentURI);
	}
	
	// A.2.6 SprayingTypeCS
	private static Vector<String> sprayingTypeTable_ =  new Vector<String>();
	public final static String SPRAYINGTYPEURIBASE = "urn:mpeg:mpeg-v:01-SI-SprayingTypeCS-NS";
	public final static String SPRAYINGTYPE_WATER = SPRAYINGTYPEURIBASE + ":water";
	
	static {
		sprayingTypeTable_.add(SPRAYINGTYPE_WATER);
	}
	
	public static Vector<String> getSprayingTypeTable() {
		return sprayingTypeTable_;
	}
	
	public static boolean containsSprayingType(String sprayingTypeURI) {
		return sprayingTypeTable_.contains(sprayingTypeURI);
	}
}
