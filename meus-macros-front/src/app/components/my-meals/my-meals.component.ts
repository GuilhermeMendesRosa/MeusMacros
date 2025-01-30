import {Component} from '@angular/core';
import {NgForOf, NgStyle} from '@angular/common';

@Component({
  selector: 'app-my-meals',
  imports: [
    NgForOf,
    NgStyle
  ],
  templateUrl: './my-meals.component.html',
  styleUrl: './my-meals.component.css'
})
export class MyMealsComponent {
  meals = [
    {
      name: 'Breakfast',
      description: 'Oatmeal with banana - 320 kcal',
      icon: 'breakfast_dining',
      color: 'amber',
      nutrients: [
        {label: 'Protein', value: '12g'},
        {label: 'Carbs', value: '45g'},
        {label: 'Fat', value: '8g'}
      ],
      tags: [
        {label: 'Whole grain', color: 'amber'},
        {label: 'Fresh fruit', color: 'green'}
      ]
    },
    {
      name: 'Lunch',
      description: 'Grilled chicken salad - 450 kcal',
      icon: 'lunch_dining',
      color: 'orange',
      nutrients: [
        {label: 'Protein', value: '38g'},
        {label: 'Carbs', value: '25g'},
        {label: 'Fat', value: '15g'}
      ],
      tags: [
        {label: 'High protein', color: 'red'},
        {label: 'Low carb', color: 'green'}
      ]
    },
    {
      name: 'Dinner',
      description: 'Salmon with vegetables - 580 kcal',
      icon: 'dinner_dining',
      color: 'blue',
      nutrients: [
        {label: 'Protein', value: '35g'},
        {label: 'Carbs', value: '75g'},
        {label: 'Fat', value: '22g'}
      ],
      tags: [
        {label: 'Omega-3', color: 'blue'},
        {label: 'Vegetables', color: 'green'}
      ]
    }
  ];

  stats = [
    {label: 'Protein', value: '85g', goal: '120g', progress: 71, color: 'blue'},
    {label: 'Carbs', value: '145g', goal: '200g', progress: 73, color: 'amber'},
    {label: 'Fat', value: '45g', goal: '65g', progress: 69, color: 'red'}
  ];
}
