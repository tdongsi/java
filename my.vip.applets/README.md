# Interactive Java applications/applets

This Eclipse project contains my past works in an internship, using then-new Java 1.4. It contains several interactive Java applications/applets that demonstrate Electrical Engineering concepts.

# Screenshots

## Transformer

In this project, the interactive application shows the relationship of the two alternating currents (AC) voltage on the two sides of an ideal transformer.

A user can change the primary and secondary windings as well as the primary (input) voltage. 

![alt text](https://dl.dropbox.com/s/bx1k0nlbcqvhak8/Transformer1.jpg "Transformer1") 

![alt text](https://dl.dropbox.com/s/3nn2vxg00cvjn6b/Transformer2.jpg "Transformer2")

The AC current is animated in application.

## Lenz's Law

In this project, an interactive application visually demonstrates the Lenz's law.

The magnet can be interactively dragged around to generate the electrical current. 
In particular, to demonstrate how an alternating current is generated in a generator or a transformer, one can use this application and drag the magnet back and forth against the black ring.

![alt text](https://dl.dropbox.com/s/ysnk6hz8v9l53mn/LenzLaw1.jpg "LenzLaw1") 

![alt text](https://dl.dropbox.com/s/sio94onaadx717i/LenzLaw2.jpg "LenzLaw2")

## Impedance transform

In this project, an interactive application demonstrates how to eliminate an ideal transformer in a circuit for impedance computation. The idea is to shift impedance to one side of the transformer, either primary or secondary.

![alt text](https://dl.dropbox.com/s/cogf6rfhvamxnty/Impedance1.jpg "Impedance1") 

The original circuit with transformer.

![alt text](https://dl.dropbox.com/s/zuipem7ma4bp1jy/Impedance2.jpg "Impedance2")

The equivalent circuit after shifting impedance to the secondary (load) side.

![alt text](https://dl.dropbox.com/s/xz9amo9yib3kj6d/Impedance3.jpg "Impedance3")

The equivalent circuit after shifting impedance to the primary (source) side. 

## Frequency Response

In this project, an interactive application demonstrates different passive analog filters, namely lowpass, highpass, bandpass, bandstop filters. Their ideal frequency response and actual frequency response will be demonstrated. Check out the picture below, extracted from a textbook, to refresh your Electrical Engineering knowledge.

![alt text](https://dl.dropbox.com/s/bnr5ilsy1ridxtk/Filters.jpg "Filters") 

Using this Java application/applet, one can:
* select different passive analog filters (e.g., lowpass, bandpass) and immediately see each filter's effect on the sinusoidal input.
* see the visualized frequency response, with the actual frequency response vs. ideal frequency response.
* adjust the base frequency and see its effect on output wave.
* see the computed characteristic frequency and see its relation to the filter output.
* adjust the parameters of the filter circuit, namely resistance, capacitance, and inductance of the circuit and see its effect on the frequency response.

![alt text](https://dl.dropbox.com/s/w9w99o6dn93jhbx/Fr1.jpg "Fr1") 

![alt text](https://dl.dropbox.com/s/v8ck6mvjj4lxsdy/Fr2.jpg "Fr2")

## Filters

In this project, the true effect of the passive analog filters in practical applications will be demonstrated. In practice, most analog signals will come as arbitrary (periodic) waves, instead of sinusoidal waves. 
We demonstrate that by examining the effect of different passive analog filters on different periodic waves, such as square and sawtooth waves. 
Additionally, we examine the additive synthesis of periodic waves with a number of sinusoidal harmonics. 
We also demonstrate the effect of the analog filters on the input wave as addition of effects on individual sinusoidal harmonics. 

Using this interactive application, one can:

* select different passive analog filters (e.g., lowpass, bandpass) and immediately see each filter's effect on input wave.
* manipulate phase and magnitude of a few harmonic components to create different input wave.
* OR use pre-programmed special periodic waves such as square and sawtooth waves.
* adjust the base frequency and see its effect on output wave.
* adjust the parameters of the filter circuit, namely resistance, capacitance, and inductance of the circuit.
* see the computed characteristic frequency and see its effect on the filter output.

![alt text](https://dl.dropbox.com/s/9uik8b3q8xzo5xf/Filters1.jpg "Filters1") 

![alt text](https://dl.dropbox.com/s/ktn4el7v84ogs4t/Filters2.jpg "Filters2") 

## Direct Current (DC) Motor

In this project, a simple DC motor circuit is shown.

![alt text](https://dl.dropbox.com/s/l3vh1zxkzaytq2s/DcMotor1.jpg "DcMotor1") 

The electric current's direction can be changed.

![alt text](https://dl.dropbox.com/s/a0nouuyv61pcnkf/DcMotor2.jpg "DcMotor2")

## Energy Flow of a DC Motor Circuit

In this project, the components of power/energy loss in different DC motor circuit configurations (e.g., shunt, series) are shown.

NOTE: The animation is done using Adobe (then Macromedia) Flash instead of Java. The fla source file and supporting image files are checked in here for record.

The project is done near the end of the three-month internship, when I was able to convince my supervising professor that using Flash is better for such a animation-heavy project and took the risk to complete it.

[Final SWF animation](https://dl.dropbox.com/s/fja9kho6psbbx3i/EnergyFlow.swf)

![alt text](https://dl.dropbox.com/s/zwnu696bhh31c67/EnergyFlow.jpg "EnergyFlow")
