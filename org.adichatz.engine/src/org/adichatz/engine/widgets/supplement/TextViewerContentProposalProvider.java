package org.adichatz.engine.widgets.supplement;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.jface.fieldassist.IContentProposalProvider;

public class TextViewerContentProposalProvider implements IContentProposalProvider {

	/** The content proposals. */
	protected IContentProposal[] contentProposals = new IContentProposal[0];

	/** The previous contents. */
	private String previousContents = "";

	private int previousPosition = -1;

	protected ITextControllerProposal textControllerProposal;

	private boolean reinit;

	/**
	 * Instantiates a new viewer content proposal.
	 * 
	 */
	public TextViewerContentProposalProvider(ITextControllerProposal textControllerProposal, boolean reinit) {
		this.textControllerProposal = textControllerProposal;
		this.reinit = reinit;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.fieldassist.IContentProposalProvider#getProposals(java.lang.String, int)
	 */
	@Override
	public IContentProposal[] getProposals(String contents, int position) {
		if (!reinit && 0 < contentProposals.length && previousContents.equals(contents) && previousPosition == position)
			return contentProposals;
		List<IContentProposal> proposalList = new ArrayList<IContentProposal>();
		for (Object element : textControllerProposal.getInitialProposals()) {
			String proposal = textControllerProposal.getLabelProvider().getText(element);
			String radix = contents.substring(0, position);
			if (0 == radix.length()
					|| (proposal.length() >= radix.length() && -1 < proposal.toUpperCase().indexOf(radix.toUpperCase()))) {
				proposalList.add(new ViewerContentProposal(element, proposal));
			}
		}
		if (0 < proposalList.size()) {
			previousContents = contents;
			previousPosition = position;
		}
		return contentProposals = proposalList.toArray(new IContentProposal[proposalList.size()]);
	}
}
