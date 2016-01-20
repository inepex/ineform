package com.inepex.ineForm.client.i18n;

import com.inepex.inei18n.shared.I18nModule;
import com.inepex.inei18n.shared.I18nModuleProvider;

public class IneFormI18n extends I18nModule {

    private static final long serialVersionUID = 1L;

    public static final String MODULE_NAME = "IneFormI18n";

    static I18nModuleProvider<IneFormI18n> moduleProvider;

    public IneFormI18n() {
        super(MODULE_NAME);
    }

    public IneFormI18n(I18nModuleProvider<IneFormI18n> moduleProvider) {
        super(MODULE_NAME);
        IneFormI18n.moduleProvider = moduleProvider;
    }

    public static String getI18nText(String key) {
        return moduleProvider.get().getText(key);
    }

    @Override
    public I18nModuleProvider<?> getI18nProvider() {
        return moduleProvider;
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> + <u><i>Magyarul:</i></u> +
     */
    public static String ADD() {
        return moduleProvider.get().getText("ADD");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Cancel <u><i>Magyarul:</i></u> Mégse
     */
    public static String CANCEL() {
        return moduleProvider.get().getText("CANCEL");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Export <u><i>Magyarul:</i></u> Excel export
     */
    public static String CSVEXPORT() {
        return moduleProvider.get().getText("CSVEXPORT");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Delete <u><i>Magyarul:</i></u> Töröl
     */
    public static String DELETE() {
        return moduleProvider.get().getText("DELETE");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Deselect <u><i>Magyarul:</i></u> Eltávolít
     */
    public static String DESELECT() {
        return moduleProvider.get().getText("DESELECT");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Deselect all <u><i>Magyarul:</i></u> Mindet
     * eltávolít
     */
    public static String DESELECTALL() {
        return moduleProvider.get().getText("DESELECTALL");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Down <u><i>Magyarul:</i></u> Le
     */
    public static String DOWN() {
        return moduleProvider.get().getText("DOWN");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Edit <u><i>Magyarul:</i></u> Módosít
     */
    public static String EDIT() {
        return moduleProvider.get().getText("EDIT");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Could not render widget <u><i>Magyarul:</i></u>
     * Hiba, nem lehet megjeleníteni a mezőt
     */
    public static String ERR_CouldNotRenderWidget() {
        return moduleProvider.get().getText("ERR_CouldNotRenderWidget");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Filter <u><i>Magyarul:</i></u> Szűrés
     */
    public static String FILTER() {
        return moduleProvider.get().getText("FILTER");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Finish <u><i>Magyarul:</i></u> Befejez
     */
    public static String FINISH() {
        return moduleProvider.get().getText("FINISH");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Down <u><i>Magyarul:</i></u> Le
     */
    public static String MOVEDOWN() {
        return moduleProvider.get().getText("MOVEDOWN");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Up <u><i>Magyarul:</i></u> Fel
     */
    public static String MOVEUP() {
        return moduleProvider.get().getText("MOVEUP");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> New <u><i>Magyarul:</i></u> Új
     */
    public static String NEW() {
        return moduleProvider.get().getText("NEW");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Next page <u><i>Magyarul:</i></u> Következő
     * oldal
     */
    public static String NEXT() {
        return moduleProvider.get().getText("NEXT");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Checkbox <u><i>Magyarul:</i></u> Igaz-Hamis
     */
    public static String ODFieldType_BOOLEAN() {
        return moduleProvider.get().getText("ODFieldType_BOOLEAN");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Fraction <u><i>Magyarul:</i></u> Tört szám
     */
    public static String ODFieldType_DOUBLE() {
        return moduleProvider.get().getText("ODFieldType_DOUBLE");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> E-mail <u><i>Magyarul:</i></u> E-mail
     */
    public static String ODFieldType_EMAIL() {
        return moduleProvider.get().getText("ODFieldType_EMAIL");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Number <u><i>Magyarul:</i></u> Szám
     */
    public static String ODFieldType_LONG() {
        return moduleProvider.get().getText("ODFieldType_LONG");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Text <u><i>Magyarul:</i></u> Szöveg
     */
    public static String ODFieldType_STRING() {
        return moduleProvider.get().getText("ODFieldType_STRING");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Ok <u><i>Magyarul:</i></u> Ok
     */
    public static String OK() {
        return moduleProvider.get().getText("OK");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Previous page <u><i>Magyarul:</i></u> Előző
     * oldal
     */
    public static String PREVIOUS() {
        return moduleProvider.get().getText("PREVIOUS");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> - <u><i>Magyarul:</i></u> -
     */
    public static String REMOVE() {
        return moduleProvider.get().getText("REMOVE");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Reset <u><i>Magyarul:</i></u> Visszaállít
     */
    public static String RESET() {
        return moduleProvider.get().getText("RESET");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Save <u><i>Magyarul:</i></u> Mentés
     */
    public static String SAVE() {
        return moduleProvider.get().getText("SAVE");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Search <u><i>Magyarul:</i></u> Keres
     */
    public static String SEARCH() {
        return moduleProvider.get().getText("SEARCH");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Select <u><i>Magyarul:</i></u> Kiválaszt
     */
    public static String SELECT() {
        return moduleProvider.get().getText("SELECT");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Select all <u><i>Magyarul:</i></u> Mindet
     * kiválaszt
     */
    public static String SELECTALL() {
        return moduleProvider.get().getText("SELECTALL");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Up <u><i>Magyarul:</i></u> Fel
     */
    public static String UP() {
        return moduleProvider.get().getText("UP");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> can't display <u><i>Magyarul:</i></u> nem
     * megjeleníthető
     */
    public static String cantDisplay() {
        return moduleProvider.get().getText("cantDisplay");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Change <u><i>Magyarul:</i></u> Megváltoztat
     */
    public static String change() {
        return moduleProvider.get().getText("change");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Click here to download: <u><i>Magyarul:</i></u>
     * Az adatok letölthetőek:
     */
    public static String csvComment() {
        return moduleProvider.get().getText("csvComment");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Download <u><i>Magyarul:</i></u> Letöltés
     */
    public static String csvDownload() {
        return moduleProvider.get().getText("csvDownload");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Something went wrong. See server log!
     * <u><i>Magyarul:</i></u> Hiba történt,
     */
    public static String csvError() {
        return moduleProvider.get().getText("csvError");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Invalid request. Try to refresh!
     * <u><i>Magyarul:</i></u> Hibás kérés, próbálja meg frissíteni az oldalt!
     */
    public static String csvInvalid() {
        return moduleProvider.get().getText("csvInvalid");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Name should not contain dot!
     * <u><i>Magyarul:</i></u> A név ne tartalmazzon pontot!
     */
    public static String custKVOValidateDot() {
        return moduleProvider.get().getText("custKVOValidateDot");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Duplicated name! <u><i>Magyarul:</i></u> Egy
     * név csak egyszer használható
     */
    public static String custKVOValidateDuplicate() {
        return moduleProvider.get().getText("custKVOValidateDuplicate");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Empty name! <u><i>Magyarul:</i></u> A név mezőt
     * ki kell tölteni!
     */
    public static String custKVOValidateEmpty() {
        return moduleProvider.get().getText("custKVOValidateEmpty");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Value can not be parsed!
     * <u><i>Magyarul:</i></u> Az érték mező nem megfelelő formátumú!
     */
    public static String custKVOValidateParse() {
        return moduleProvider.get().getText("custKVOValidateParse");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Type must be set! <u><i>Magyarul:</i></u> A
     * mező típusát be kell állítani!
     */
    public static String custKVOValidateSet() {
        return moduleProvider.get().getText("custKVOValidateSet");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Name <u><i>Magyarul:</i></u> Név
     */
    public static String customKVO_key() {
        return moduleProvider.get().getText("customKVO_key");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Type <u><i>Magyarul:</i></u> Típus
     */
    public static String customKVO_type() {
        return moduleProvider.get().getText("customKVO_type");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Value <u><i>Magyarul:</i></u> Érték
     */
    public static String customKVO_value() {
        return moduleProvider.get().getText("customKVO_value");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Friday <u><i>Magyarul:</i></u> péntek
     */
    public static String day_friday() {
        return moduleProvider.get().getText("day_friday");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Monday <u><i>Magyarul:</i></u> hétfő
     */
    public static String day_monday() {
        return moduleProvider.get().getText("day_monday");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Saturday <u><i>Magyarul:</i></u> szombat
     */
    public static String day_saturday() {
        return moduleProvider.get().getText("day_saturday");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Sunday <u><i>Magyarul:</i></u> vasárnap
     */
    public static String day_sunday() {
        return moduleProvider.get().getText("day_sunday");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Thursday <u><i>Magyarul:</i></u> csütörtök
     */
    public static String day_thursday() {
        return moduleProvider.get().getText("day_thursday");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Tuesday <u><i>Magyarul:</i></u> kedd
     */
    public static String day_tuesday() {
        return moduleProvider.get().getText("day_tuesday");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Wednesday <u><i>Magyarul:</i></u> szerda
     */
    public static String day_wednesday() {
        return moduleProvider.get().getText("day_wednesday");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Edit entry <u><i>Magyarul:</i></u> Sor
     * módosítása
     */
    public static String dialogEditTitle() {
        return moduleProvider.get().getText("dialogEditTitle");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> New entry <u><i>Magyarul:</i></u> Új sor
     */
    public static String dialogNewTitle() {
        return moduleProvider.get().getText("dialogNewTitle");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> false <u><i>Magyarul:</i></u> nem
     */
    public static String falseText() {
        return moduleProvider.get().getText("falseText");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> hours <u><i>Magyarul:</i></u> óra
     */
    public static String hours() {
        return moduleProvider.get().getText("hours");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Choose images from Google image search
     * <u><i>Magyarul:</i></u> Kép választása a Google képkereső segítségével
     */
    public static String imagefinderChoosefromgoogle() {
        return moduleProvider.get().getText("imagefinderChoosefromgoogle");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Downloading... <u><i>Magyarul:</i></u> Letöltés
     * folyamatban...
     */
    public static String imagefinderDowloading() {
        return moduleProvider.get().getText("imagefinderDowloading");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Failed to download... <u><i>Magyarul:</i></u>
     * Hiba a letöltés során
     */
    public static String imagefinderFailed() {
        return moduleProvider.get().getText("imagefinderFailed");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> No file <u><i>Magyarul:</i></u> nincs fájl
     */
    public static String imagefinderNoimage() {
        return moduleProvider.get().getText("imagefinderNoimage");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> 0 image found <u><i>Magyarul:</i></u> 0 találat
     */
    public static String imagefinderNoresult() {
        return moduleProvider.get().getText("imagefinderNoresult");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Page <u><i>Magyarul:</i></u> Oldal
     */
    public static String imagefinderPage() {
        return moduleProvider.get().getText("imagefinderPage");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Downloaded <u><i>Magyarul:</i></u> Letöltve
     */
    public static String imagefinderSuccess() {
        return moduleProvider.get().getText("imagefinderSuccess");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Upload <u><i>Magyarul:</i></u> Válassza ki a
     * feltöltendő fájlt
     */
    public static String imagefinderUploadimage() {
        return moduleProvider.get().getText("imagefinderUploadimage");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Use <u><i>Magyarul:</i></u> Kiválaszt
     */
    public static String imagefinderUse() {
        return moduleProvider.get().getText("imagefinderUse");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Upload <u><i>Magyarul:</i></u> Feltöltés
     */
    public static String imageuploadBtn() {
        return moduleProvider.get().getText("imageuploadBtn");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Change <u><i>Magyarul:</i></u> Csere
     */
    public static String imageuploadBtn_change() {
        return moduleProvider.get().getText("imageuploadBtn_change");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Error during upload. See server log!
     * <u><i>Magyarul:</i></u> Hiba a feltöltés során. Ellenőrizze a szerver
     * beállításait.
     */
    public static String imageuploadError() {
        return moduleProvider.get().getText("imageuploadError");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Invalid file format. <u><i>Magyarul:</i></u>
     * Nem megfelelő fájlformátum
     */
    public static String imageuploadInvalidFileFormat() {
        return moduleProvider.get().getText("imageuploadInvalidFileFormat");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Upload <u><i>Magyarul:</i></u> Feltöltés
     */
    public static String imageuploadTitle() {
        return moduleProvider.get().getText("imageuploadTitle");
    }

    /**
     * <u><i>Description:</i></u> $loc.getDescription() <br />
     * <u><i>In English:</i></u> Not set <u><i>Magyarul:</i></u> Nincs megadva
     */
    public static String inedate_notset() {
        return moduleProvider.get().getText("inedate_notset");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> No result found <u><i>Magyarul:</i></u> Nincs
     * találat
     */
    public static String inetable_noresult() {
        return moduleProvider.get().getText("inetable_noresult");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Message <u><i>Magyarul:</i></u> Üzenet
     */
    public static String infoDialogTitle() {
        return moduleProvider.get().getText("infoDialogTitle");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Loading data... <u><i>Magyarul:</i></u> Adatok
     * betöltése...
     */
    public static String loading() {
        return moduleProvider.get().getText("loading");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> minutes <u><i>Magyarul:</i></u> perc
     */
    public static String minutes() {
        return moduleProvider.get().getText("minutes");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> April <u><i>Magyarul:</i></u> Április
     */
    public static String month_april() {
        return moduleProvider.get().getText("month_april");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> August <u><i>Magyarul:</i></u> Augusztus
     */
    public static String month_august() {
        return moduleProvider.get().getText("month_august");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> December <u><i>Magyarul:</i></u> December
     */
    public static String month_december() {
        return moduleProvider.get().getText("month_december");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Februar <u><i>Magyarul:</i></u> Február
     */
    public static String month_february() {
        return moduleProvider.get().getText("month_february");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Januar <u><i>Magyarul:</i></u> Január
     */
    public static String month_january() {
        return moduleProvider.get().getText("month_january");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> July <u><i>Magyarul:</i></u> Július
     */
    public static String month_july() {
        return moduleProvider.get().getText("month_july");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> June <u><i>Magyarul:</i></u> Június
     */
    public static String month_june() {
        return moduleProvider.get().getText("month_june");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> March <u><i>Magyarul:</i></u> Március
     */
    public static String month_march() {
        return moduleProvider.get().getText("month_march");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> May <u><i>Magyarul:</i></u> Május
     */
    public static String month_may() {
        return moduleProvider.get().getText("month_may");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> November <u><i>Magyarul:</i></u> November
     */
    public static String month_november() {
        return moduleProvider.get().getText("month_november");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> October <u><i>Magyarul:</i></u> Október
     */
    public static String month_october() {
        return moduleProvider.get().getText("month_october");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> September <u><i>Magyarul:</i></u> Szeptember
     */
    public static String month_september() {
        return moduleProvider.get().getText("month_september");
    }

    /**
     * <u><i>Description:</i></u> $loc.getDescription() <br />
     * <u><i>In English:</i></u> nd <u><i>Magyarul:</i></u>
     */
    public static String nd() {
        return moduleProvider.get().getText("nd");
    }

    /**
     * <u><i>Description:</i></u> $loc.getDescription() <br />
     * <u><i>In English:</i></u> Not set <u><i>Magyarul:</i></u> Nincs beállítva
     */
    public static String notSetText() {
        return moduleProvider.get().getText("notSetText");
    }

    /**
     * <u><i>Description:</i></u> $loc.getDescription() <br />
     * <u><i>In English:</i></u> rd <u><i>Magyarul:</i></u>
     */
    public static String rd() {
        return moduleProvider.get().getText("rd");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Do you really want to delete the selected
     * element? <u><i>Magyarul:</i></u> Biztosan törölni kívánja a kiválaszott
     * sort?
     */
    public static String reallyWantToDelete() {
        return moduleProvider.get().getText("reallyWantToDelete");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Request error <u><i>Magyarul:</i></u> Hiba a
     * kérés során
     */
    public static String restRequestError() {
        return moduleProvider.get().getText("restRequestError");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Result is filtered! <u><i>Magyarul:</i></u> Az
     * eredmények szűrve vannak.
     */
    public static String searchForm_filtered() {
        return moduleProvider.get().getText("searchForm_filtered");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> F <u><i>Magyarul:</i></u> P
     */
    public static String shortday_friday() {
        return moduleProvider.get().getText("shortday_friday");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> M <u><i>Magyarul:</i></u> H
     */
    public static String shortday_monday() {
        return moduleProvider.get().getText("shortday_monday");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Sa <u><i>Magyarul:</i></u> Szo
     */
    public static String shortday_saturday() {
        return moduleProvider.get().getText("shortday_saturday");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Su <u><i>Magyarul:</i></u> V
     */
    public static String shortday_sunday() {
        return moduleProvider.get().getText("shortday_sunday");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Th <u><i>Magyarul:</i></u> Cs
     */
    public static String shortday_thursday() {
        return moduleProvider.get().getText("shortday_thursday");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Tu <u><i>Magyarul:</i></u> K
     */
    public static String shortday_tuesday() {
        return moduleProvider.get().getText("shortday_tuesday");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> W <u><i>Magyarul:</i></u> Sze
     */
    public static String shortday_wednesday() {
        return moduleProvider.get().getText("shortday_wednesday");
    }

    /**
     * <u><i>Description:</i></u> $loc.getDescription() <br />
     * <u><i>In English:</i></u> st <u><i>Magyarul:</i></u>
     */
    public static String st() {
        return moduleProvider.get().getText("st");
    }

    /**
     * <u><i>Description:</i></u> $loc.getDescription() <br />
     * <u><i>In English:</i></u> th <u><i>Magyarul:</i></u>
     */
    public static String th() {
        return moduleProvider.get().getText("th");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> true <u><i>Magyarul:</i></u> igen
     */
    public static String trueText() {
        return moduleProvider.get().getText("trueText");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> This is not valid e-mail address!
     * <u><i>Magyarul:</i></u> Nem megfelelő az e-mail cím!
     */
    public static String validationEmail() {
        return moduleProvider.get().getText("validationEmail");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Field error <u><i>Magyarul:</i></u> Hibás mező
     * -
     */
    public static String validationFieldError() {
        return moduleProvider.get().getText("validationFieldError");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Error: <u><i>Magyarul:</i></u> Hiba:
     */
    public static String validationGeneralError() {
        return moduleProvider.get().getText("validationGeneralError");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Nothing to save <u><i>Magyarul:</i></u> Nincs
     * változás a formon
     */
    public static String validationNothingToSave() {
        return moduleProvider.get().getText("validationNothingToSave");
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> The number entered into field {fieldName} must
     * be equal to {constvalAsString}! <u><i>Magyarul:</i></u> A {fieldName}
     * mezőbe beírt szám értéke {constvalAsString} kell, hogy legyen!
     */
    public static String validatorEQ(String constvalAsString, String fieldName) {
        return moduleProvider
            .get()
            .getText("validatorEQ")
            .replace("{constvalAsString}", constvalAsString)
            .replace("{fieldName}", fieldName);
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> The number entered into field {fieldName} must
     * be greater then or equal to {constvalAsString}! <u><i>Magyarul:</i></u> A
     * {fieldName} mezőbe beírt számnak nagyobb-egyenlőnek kell lennie, mint
     * {constvalAsString}!
     */
    public static String validatorGE(String constvalAsString, String fieldName) {
        return moduleProvider
            .get()
            .getText("validatorGE")
            .replace("{constvalAsString}", constvalAsString)
            .replace("{fieldName}", fieldName);
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> The number entered into field {fieldName} must
     * be greater then {constvalAsString}! <u><i>Magyarul:</i></u> A {fieldName}
     * mezőbe beírt számnak nagyobbnak kell lennie, mint {constvalAsString}!
     */
    public static String validatorGT(String constvalAsString, String fieldName) {
        return moduleProvider
            .get()
            .getText("validatorGT")
            .replace("{constvalAsString}", constvalAsString)
            .replace("{fieldName}", fieldName);
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> The number entered into field {fieldName} must
     * be less then or equal to {constvalAsString}! <u><i>Magyarul:</i></u> A
     * {fieldName} mezőbe beírt számnak kisebb-egyenlőnek kell lennie, mint
     * {constvalAsString}!
     */
    public static String validatorLE(String constvalAsString, String fieldName) {
        return moduleProvider
            .get()
            .getText("validatorLE")
            .replace("{constvalAsString}", constvalAsString)
            .replace("{fieldName}", fieldName);
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> he number entered into field {fieldName} must
     * be less then {constvalAsString}! <u><i>Magyarul:</i></u> A {fieldName}
     * mezőbe beírt számnak kisebbnek kell lennie, mint {constvalAsString}!
     */
    public static String validatorLT(String constvalAsString, String fieldName) {
        return moduleProvider
            .get()
            .getText("validatorLT")
            .replace("{constvalAsString}", constvalAsString)
            .replace("{fieldName}", fieldName);
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> Maximum {maxLength} characters can be entered!
     * <u><i>Magyarul:</i></u> Legfeljebb {maxLength} karaktert lehet írni a
     * mezőbe!
     */
    public static String validatorLength(String maxLength) {
        return moduleProvider.get().getText("validatorLength").replace("{maxLength}", maxLength);
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> This date should be after {otherFieldsName}!
     * <u><i>Magyarul:</i></u> A dámum {otherFieldsName} után kell, hogy legyen!
     */
    public static String validatorShouldAfter(String otherFieldsName) {
        return moduleProvider
            .get()
            .getText("validatorShouldAfter")
            .replace("{otherFieldsName}", otherFieldsName);
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> This date should be before {otherFieldsName}!
     * <u><i>Magyarul:</i></u> A dámum {otherFieldsName} előtt kell, hogy
     * legyen!
     */
    public static String validatorShouldBefore(String otherFieldsName) {
        return moduleProvider
            .get()
            .getText("validatorShouldBefore")
            .replace("{otherFieldsName}", otherFieldsName);
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> The {i}th and the {j}th items are equal.
     * <u><i>Magyarul:</i></u> A listában a {i}. és a {j}. elem megegyezik.
     */
    public static String validatorUniqueRelList(String i, String j) {
        return moduleProvider
            .get()
            .getText("validatorUniqueRelList")
            .replace("{i}", i)
            .replace("{j}", j);
    }

    /**
     * <u><i>Description:</i></u> <br />
     * <u><i>In English:</i></u> This field is required! <u><i>Magyarul:</i></u>
     * A mező kitöltése kötelező!
     */
    public static String validator_mandatory() {
        return moduleProvider.get().getText("validator_mandatory");
    }
}
