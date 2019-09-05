using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace LightSwitch
{
    public partial class Form1 : Form
    {

        private string str1, str2;
        private int clickCount;
        public Form1()
        {
            InitializeComponent();
            str1 = "off";
            str2 = "on";
            button1.Text = str1;

        }

        private void button1_Click(object sender, EventArgs e)
        {
            button1.Text = str2;
            str2 = str1;
            str1 = button1.Text;
            label1.Text = Convert.ToString(++clickCount, 2);

        }
    }
}
