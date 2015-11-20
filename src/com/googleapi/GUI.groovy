package com.googleapi

import groovy.swing.SwingBuilder

import java.awt.BorderLayout
import java.awt.Color
import java.awt.FlowLayout
import java.awt.event.ActionListener
import java.awt.event.ItemListener

import javax.swing.JButton
import javax.swing.JComboBox
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JOptionPane
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTextArea
import javax.swing.JTextField

class GUI extends SwingBuilder{
	
	Services services = new Services()
	JFrame frame
	JPanel contentPane
	JPanel topPanel
	JPanel bottomPanel
	JPanel centerPanel
	JTextField txtURL, paramName , paramValue
	JComboBox cboService
	JComboBox cboMethod
	def btnSubmit , btnAdd
	JTextArea showResult 
	
	def edt(){
		contentPane = new JPanel()
		topPanel = new JPanel()
		centerPanel = new JPanel()
		centerPanel.setLayout(new BorderLayout())
		bottomPanel = new JPanel()
		bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT))
		BorderLayout layout = new BorderLayout()
		contentPane.setLayout(layout)
		contentPane.add(topPanel , BorderLayout.NORTH)
		contentPane.add(centerPanel , BorderLayout.CENTER)
		contentPane.add(bottomPanel , BorderLayout.SOUTH)
		
		frame = new JFrame(
			title:'Google Map API' ,
			size:[1020 , 400],
			defaultCloseOperation:JFrame.EXIT_ON_CLOSE,
			locationRelativeTo:null , contentPane:contentPane
			)
		
		JLabel lblMethod = new JLabel(text:'Method :')
		cboMethod = new JComboBox()
		cboMethod.addItem('GET')
		cboMethod.addItem('POST')
		cboMethod.addItem('PUT')
		cboMethod.addItem('DELETE')
		cboMethod.addItem('HEAD')
		cboMethod.addItem('OPTION')
		cboMethod.addItem('TRACE')
		cboMethod.addItem('PATCH')
		
		JLabel lblURL = new JLabel(text:'URL :')
		txtURL = new JTextField(text:'https://maps.googleapis.com/maps/api/directions/xml',columns:40, editable:true)
		txtURL.setForeground(Color.GRAY)
		
		JLabel lblService = new JLabel(text:'Service :')
		cboService = new JComboBox()
		
		cboService.addItem('Direction')
		cboService.addItem('Distance')
		cboService.addItem('Elevation')
		cboService.addItem('Geocoding')
		cboService.addItem('Geolocation')
		cboService.addItem('Roads')
		cboService.addItem('Time Zone')
		cboService.addItem('API Web Service')	
		cboService.addItemListener(itemListener as ItemListener)
		
		btnSubmit = new JButton(text:'submit')
		btnSubmit.addActionListener(clickSubmit as ActionListener)
		showResult = new JTextArea()
		
		paramName = new JTextField(columns:10)
		paramValue = new JTextField(columns:10)
		
		btnAdd = new JButton(text:'Add')
		btnAdd.addActionListener(clickAdd as ActionListener)
		topPanel.add(lblMethod)
		topPanel.add(cboMethod)
		topPanel.add(lblURL)
		topPanel.add(txtURL)
		topPanel.add(lblService)
		topPanel.add(cboService)
		topPanel.add(btnSubmit)
		
		centerPanel.add(new JScrollPane(showResult))
		
		bottomPanel.add(new JLabel('Parameter -'))
		bottomPanel.add(new JLabel('Name :'))
		bottomPanel.add(paramName)
		bottomPanel.add(new JLabel('Value :'))
		bottomPanel.add(paramValue)
		bottomPanel.add(btnAdd)
		
		
		frame.show()
		
	}

	
	def clickSubmit = {
		if(!txtURL.text.contains('?'))
			JOptionPane.showMessageDialog(frame , 'Error : No Parameter found.')
		else if(cboService.getSelectedIndex() == 3){showResult.append(services.geocodeAPI())}
		else if(cboService.getSelectedIndex() == 0){showResult.append(services.directionAPI())}
		
	}
	
	def itemListener = {
		if(cboService.getSelectedIndex() == 0){
			txtURL.text = "${services.URL[0]}${services.path[0]}${services.service[0]}/${services.output[0]}"
		}
		if(cboService.getSelectedIndex() == 1){
			txtURL.text = "${services.URL[0]}${services.path[0]}${services.service[1]}/${services.output[0]}"
		}
		if(cboService.getSelectedIndex() == 2){
			txtURL.text = "${services.URL[0]}${services.path[0]}${services.service[2]}/${services.output[0]}"
		}
		if(cboService.getSelectedIndex() == 3){
			txtURL.text = "${services.URL[0]}${services.path[0]}${services.service[3]}/${services.output[0]}"
		}
		
	}
	
	def clickAdd = {
		if(paramName.text.trim().equals("") || paramValue.text.trim().equals("")){
			JOptionPane.showMessageDialog(frame , 'Error : Empty input has found.')	
		}else
		{	 if(!txtURL.text.contains('?')){
				txtURL.text += "?${paramName.text}=${paramValue.text}"
				services.params << ["$paramName.text" : "$paramValue.text"]
			}
			else{
				txtURL.text += "&${paramName.text}=${paramValue.text}"
				services.params << ["$paramName.text" : "$paramValue.text"]
			}
		}
				
		paramValue.text = ''
		paramName.text = ''
		
	}
}