package com.inepex.ineForm.client.form.widgets.upload;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.inepex.ineForm.client.general.IneButton;
import com.inepex.ineForm.client.general.IneButton.IneButtonType;
import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.ineForm.shared.upload.UrlDownloadService;
import com.inepex.ineForm.shared.upload.UrlDownloadServiceAsync;

public class ImageFinderGoogle extends VerticalPanel {
	private static UrlDownloadServiceAsync urlDownloadService = (UrlDownloadServiceAsync) GWT
	.create(UrlDownloadService.class);
	private FileUploadFw parent;
	
	HorizontalPanel panel_search = new HorizontalPanel();
	
	TextBox searchText = new TextBox();
	
	IneButton btn_search = new IneButton(IneButtonType.ACTION, "Search");
	
	HorizontalPanel panel_images = new HorizontalPanel();
	
	Grid panel_pager = new Grid(1, 3);
		
	int page = 0;
	
	int attaching = 0;
	
	HTML title = new HTML("<h3>" + IneFormI18n.imagefinderChoosefromgoogle() + "</h3>");
	IneButton previous = new IneButton(IneButtonType.PAGING, IneFormI18n.PREVIOUS());
	IneButton next = new IneButton(IneButtonType.PAGING, IneFormI18n.NEXT());
	Label lbl_actualpage = new Label(IneFormI18n.imagefinderPage() + ": " + String.valueOf(page));
	Label lbl_noresult = new Label(IneFormI18n.imagefinderNoresult());
	
//	private class ImageFinderImage extends Composite{
//		
//		Grid grid = new Grid(2, 1);
//		
//		String URL;
//		
//		Image image = null;
//		Button useImage = new Button(IneFormI18n.imagefinderUse());
//		
//		HandlerRegistration handler = null;
//		
//		public ImageFinderImage(String thumbnailURL, String URL) {
//			this.URL = URL;
//			
//			initWidget(grid);
//			addStyleName("ImageFinderImage");
//			
//			image = new Image(thumbnailURL);
//			
//			grid.getRowFormatter().addStyleName(0, "ImageFinderRow1");
//			grid.setWidget(0, 0, image);
//			grid.setWidget(1, 0, useImage);
//			
//			
//			handler = useImage.addClickHandler(new ClickHandler() {
//				
//				@Override
//				public void onClick(ClickEvent event) {
//					downloadAndStoreImage();					
//				}
//			});
//			
//			image.addClickHandler(new ClickHandler() {
//				
//				@Override
//				public void onClick(ClickEvent event) {
//					Window.open(ImageFinderImage.this.URL, "_blank", "");
//					
//				}
//			});
//		}
//		
//		private void downloadAndStoreImage(){
//			useImage.setText(IneFormI18n.imagefinderDowloading());
//
//			handler.removeHandler();
//			
//			urlDownloadService.downloadImageFromUrl(URL, new AsyncCallback<String>() {
//
//				@Override
//				public void onFailure(Throwable caught) {
//					useImage.setText(IneFormI18n.imagefinderFailed());
//				}
//
//				@Override
//				public void onSuccess(String result) {
//					if (!result.equals("\n")){
//						useImage.setText(IneFormI18n.imagefinderSuccess());
//						parent.setStringValue(result);
//						parent.hidePopup();
//					} else {
//						useImage.setText(IneFormI18n.imagefinderFailed());
//					}					
//				}
//			});
//		}
//	}

	public ImageFinderGoogle(FileUploadFw parent) {
		this.parent = parent;

//		SearchUtils.loadSearchApi(new Runnable() {
//			@Override
//			public void run() {
//				final ImageSearch imageSearch = new ImageSearch();
//				SearchControl control = null;
//				add(title);
//				add(panel_search);
//				addStyleName("ImageFinderGoogle");
//
//				panel_search.add(searchText);
//				panel_search.add(btn_search);
//				
//				btn_search.addClickHandler(new ClickHandler() {
//					
//					@Override
//					public void onClick(ClickEvent event) {
//						clearResultsAndExecuteSearch();
//					}
//					
//				});
//				
//				searchText.addKeyPressHandler(new KeyPressHandler() {
//					
//					@Override
//					public void onKeyPress(KeyPressEvent event) {
//						if (event.getCharCode() == KeyCodes.KEY_ENTER){
//							clearResultsAndExecuteSearch();
//						}
//					}
//				});
//				
//				add(panel_pager);
//				add(lbl_noresult);
//				panel_pager.setVisible(false);
//				panel_pager.setWidget(0, 0, previous);
//				panel_pager.setWidget(0, 1, lbl_actualpage);
//				panel_pager.setWidget(0, 2, next);
//				panel_pager.getCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_CENTER);
//				panel_pager.getCellFormatter().setHorizontalAlignment(0, 2, HasHorizontalAlignment.ALIGN_RIGHT);
//				panel_pager.addStyleName("PagerPanel");
//				
//				add(panel_images);
//				panel_images.setHeight("140px");
//				
//				SearchControlOptions options = new SearchControlOptions();
//				options.add(imageSearch, ExpandMode.OPEN);
//				imageSearch.setNoHtmlGeneration();		
//				imageSearch.setResultSetSize(ResultSetSize.LARGE);
//
//				control = new SearchControl(options);
//				
//				control.addSearchResultsHandler(new SearchResultsHandler(){
//
//					@Override
//					public void onSearchResults(SearchResultsEvent event) {
//						for (int i = 0; i<event.getResults().length(); i++) {
//							addImageToResults((ImageResult)event.getResults().get(i));
//						}
//					}
//					
//				});
//
//				next.addClickHandler(new ClickHandler() {
//					
//					@Override
//					public void onClick(ClickEvent event) {
//						panel_images.clear();
//						imageSearch.gotoPage(++page);
//						lbl_actualpage.setText("Page: " + String.valueOf(page));
//						
//					}
//				});
//				
//				previous.addClickHandler(new ClickHandler() {
//					
//					@Override
//					public void onClick(ClickEvent event) {
//						if (page > 0){
//						panel_images.clear();
//						imageSearch.gotoPage(--page);
//						lbl_actualpage.setText("Page: " + String.valueOf(page));
//						}
//						
//					}
//				});				
//			}
//		});
		

	}
	
//	private void addImageToResults(ImageResult result){
//		if (lbl_noresult.isVisible()){
//			lbl_noresult.setVisible(false);
//			panel_pager.setVisible(true);
//		}
//		panel_images.add(new ImageFinderImage(result.getThumbnailUrl(), result.getUrl()));
//	}
	
	public void setDefaultSearchValue(String defaultSearchValue){
		searchText.setText(defaultSearchValue);
		if (!defaultSearchValue.equals("")){
			clearResultsAndExecuteSearch();
		}
	}
	
	private void clearResultsAndExecuteSearch(){
		panel_images.clear();
//		control.execute(searchText.getText());
	}
}
