 i m p o r t   j a v a . u t i l . S c a n n e r ;   
 p u b l i c   c l a s s   F l o w C o d e { 
         p u b l i c   s t a t i c   v o i d   m a i n ( S t r i n g [ ]   a r g s ) { 
                 S c a n n e r   s c   =   n e w   S c a n n e r ( S y s t e m . i n ) ; 
                 / /   W r i t e   y o u r   o w n   D e c l a r a t i o n . 
                                 / / W r i t e   y o u r   o w n   a s s i g n m e n t s 
                                 S y s t e m . o u t . p r i n t l n (   " W e l c o m e ,   e n t e r   y o u r   f i r s t   g u e s s   :   "   ) ; 
                 m y G u e s s   =   s c . n e x t I n t ( ) ; 
                 / /   F i l l   i n   t h e   c o n t e n t   o f   t h i s   w h i l e   l o o p . 
                 w h i l e ( )   { 
                         / /   F i l l   i n   t h e   c o n t e n t   o f   t h i s   i f   s t a t e m e n t . 
                         i f ( )   { 
                                 S y s t e m . o u t . p r i n t l n (   " H i g h e r ,   t r y   a g a i n   :   "   ) ; 
                         }   e l s e   { 
                                 S y s t e m . o u t . p r i n t l n (   " L o w e r ,   t r y   a g a i n   :   "   ) ; 
                         } 
                         m y G u e s s   =   s c . n e x t I n t ( ) ; 
                 } 
                 S y s t e m . o u t . p r i n t l n (   " T h a t ' s   c o r r e c t . "   ) ; 
                 s c . c l o s e ( ) ; 
         } 
 }