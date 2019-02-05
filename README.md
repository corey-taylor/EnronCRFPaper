# Enron Paper
Code for analysis in the following paper: 

Taylor, Leibbrandt, Powers, "Anywhere but Here - Enron’s Emails in the Midst of a Crisis" ICAART 2015 Proceedings of the International Conference on Agents and Artificial Intelligence, 2, pp 247-253

Don't think MALLET was on Github at the time so this is code is well out of date. This is purely for posterity/potential employers.

/src/cc/mallet/fst/test.java does all the work calling the CRF and the Topic model and stuffing it with sample phrases to train the CRF model before spitting out all the different types of phrases from the Enron email corpus that match the sentiment. Frequencies are then plotted.

A topic model is also trained and highest probability topics spat out to see how certain topics changed over 5 years of emails.
