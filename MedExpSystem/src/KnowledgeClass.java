import java.util.Scanner;

class KnowledgeClass {
    private String PROBLEM_TYPE;
    private boolean IS_PAIN;
    private String SECTION_BODY;

    KnowledgeClass(String ptype)
    {
        this.PROBLEM_TYPE= ptype;
    }

    KnowledgeClass(String ptype, boolean isPain, String bodysec)
    {
        this.PROBLEM_TYPE= ptype;
        this.IS_PAIN= isPain;
        this.SECTION_BODY= bodysec;
    }

    void performAnalysis()
    {
        Scanner sc= new Scanner(System.in);

        System.out.println("Type '1' --> (Yes) OR '0' --> (No) for the following symptoms");

        boolean[] x= new boolean[10];
        int input;
        double count;

        if(this.PROBLEM_TYPE.equalsIgnoreCase("Physical"))
        {
            if(this.IS_PAIN)        //Pain related problem
            {
                if(this.SECTION_BODY.equalsIgnoreCase("Upper"))
                {
                    //Heart Attack
                    //Stroke

                    System.out.println("Pain/ Pressure/ Squeezing in the centre of the chest?");
                    input= sc.nextInt();
                    x[0]= input == 1;
                    System.out.println("Shortness of breath/ Nausea/ Dizziness?");
                    input= sc.nextInt();
                    x[1]= input == 1;
                    System.out.println("Loss of vision?");
                    input= sc.nextInt();
                    x[2]= input == 1;
                    System.out.println("Difficulty with speech?");
                    input= sc.nextInt();
                    x[3]= input == 1;
                    System.out.println("Weakness in other upper body parts?");
                    input= sc.nextInt();
                    x[4]= input == 1;

                    if(x[0] || x[1] || x[4])
                    {
                        //Heart Attack
                        count= 0;
                        if(x[0])
                            count++;
                        if(x[1])
                            count++;
                        if(x[4])
                            count++;
                        System.out.println("Chances of Heart Attack: "+ (count/3)*100);
                    }

                    if(x[1] || x[2] || x[3] || x[4])
                    {
                        //Stroke
                        count= 0;
                        if(x[1])
                            count++;
                        if(x[2])
                            count++;
                        if(x[3])
                            count++;
                        if(x[4])
                            count++;
                        System.out.println("Chances of Heart Attack: "+ (count/3)*100);
                    }
                }
                else if(this.SECTION_BODY.equalsIgnoreCase("Middle"))
                {
                    //Arthritis
                    //Food Poisoning

                    System.out.println("Pain/ Pressure/ Squeezing in any bone joints?");
                    input= sc.nextInt();
                    x[0]= input == 1;
                    System.out.println("Tenderness/ Stiffness/ Inflammation around the joints?");
                    input= sc.nextInt();
                    x[1]= input == 1;
                    System.out.println("Restricted movement of the joints?");
                    input= sc.nextInt();
                    x[2]= input == 1;
                    System.out.println("Warm/ Red skin around any joint?");
                    input= sc.nextInt();
                    x[3]= input == 1;
                    System.out.println("Nausea/ Vomiting/ Diarrhoea/ Tummy pain?");
                    input= sc.nextInt();
                    x[4]= input == 1;
                    System.out.println("Loss of appetite?");
                    input= sc.nextInt();
                    x[5]= input == 1;

                    if(x[0] || x[1] || x[2] || x[3])
                    {
                        //Arthritis
                        count= 0;
                        if(x[0])
                            count++;
                        if(x[1])
                            count++;
                        if(x[2])
                            count++;
                        if(x[3])
                            count++;
                        System.out.println("Chances of Arthritis: "+ (count/4)*100);
                    }

                    if(x[5] || x[4])
                    {
                        //Food poisoning
                        count= 0;
                        if(x[5])
                            count++;
                        if(x[4])
                            count++;
                        System.out.println("Chances of Food Poisoning: "+ (count/2)*100);
                    }
                }
                else if(this.SECTION_BODY.equalsIgnoreCase("Lower"))
                {
                    //Kidney Stones

                    System.out.println("Persistent pain in the back?");
                    input= sc.nextInt();
                    x[0]= input == 1;
                    System.out.println("Feeling restless/ unable to stay still?");
                    input= sc.nextInt();
                    x[1]= input == 1;
                    System.out.println("Pain during urination?");
                    input= sc.nextInt();
                    x[2]= input == 1;
                    System.out.println("Blood in urine?");
                    input= sc.nextInt();
                    x[3]= input == 1;

                    count= 0;
                    if(x[0])
                        count++;
                    if(x[1])
                        count++;
                    if(x[2])
                        count++;
                    if(x[3])
                        count++;
                    System.out.println("Chances of Kidney Stones: "+ (count/4)*100);
                }
                else
                {
                    //error in body section

                    System.out.println("There has been an error in body section selection!");
                }
            }
            else        //Not a pain related problem
            {
                //Allergies
                //Common Cold

                System.out.println("Sneezing/ Runny blocked nose?");
                input= sc.nextInt();
                x[0]= input == 1;
                System.out.println("Itchy/ Red/ Watery eyes?");
                input= sc.nextInt();
                x[1]= input == 1;
                System.out.println("Raised/ Itchy/ Red rash?");
                input= sc.nextInt();
                x[2]= input == 1;
                System.out.println("Sore throat/ Cough?");
                input= sc.nextInt();
                x[3]= input == 1;
                System.out.println("Fever/ Head Ache?");
                input= sc.nextInt();
                x[4]= input == 1;

                if(x[0] || x[1] || x[2])
                {
                    //Allergies
                    count= 0;
                    if(x[0])
                        count++;
                    if(x[1])
                        count++;
                    if(x[2])
                        count++;
                    System.out.println("Chances of Allergies: "+ (count/3)*100);
                }

                if(x[0] || x[3] || x[4])
                {
                    //Common Cold
                    count= 0;
                    if(x[0])
                        count++;
                    if(x[3])
                        count++;
                    if(x[4])
                        count++;
                    System.out.println("Chances of Common Cold: "+ (count/3)*100);
                }
            }
        }
        else if(this.PROBLEM_TYPE.equalsIgnoreCase("Emotional"))
        {
            //Alzheimer's
            //Depression
            //Bipolar

            System.out.println("Confusion/ Disorientation/ Problem with speech?");
            input= sc.nextInt();
            x[0]= input == 1;
            System.out.println("Experiencing Anxiety?");
            input= sc.nextInt();
            x[1]= input == 1;
            System.out.println("Increasing age? (>40yrs)");
            input= sc.nextInt();
            x[2]= input == 1;
            System.out.println("Feeling sad/ Hopeless/ Irritable/ Lacking energy/ Self doubt?");
            input= sc.nextInt();
            x[3]= input == 1;
            System.out.println("Sudden feeling of elation/ happiness?");
            input= sc.nextInt();
            x[4]= input == 1;
            System.out.println("Sudden feeling of energy after a session of being sad?");
            input= sc.nextInt();
            x[5]= input == 1;
            System.out.println("Easily distracted/ irritated?");
            input= sc.nextInt();
            x[6]= input == 1;
            System.out.println("No eating/ sleeping?");
            input= sc.nextInt();
            x[7]= input == 1;

            if(x[0] || x[1] || x[2])
            {
                //Alzheimer's
                count= 0;
                if(x[0])
                    count++;
                if(x[1])
                    count++;
                if(x[2])
                    count++;
                System.out.println("Chances of Alzheimer's: "+ (count/3)*100);
            }

            if(x[3])
            {
                //Depression
                System.out.println("Chances of Depression: "+ 100);
            }

            if(x[5] || x[4] || x[6] || x[7])
            {
                //Bipolar
                count= 0;
                if(x[5])
                    count++;
                if(x[4])
                    count++;
                if(x[6])
                    count++;
                if(x[7])
                    count++;
                System.out.println("Chances of Bipolar: "+ (count/4)*100);
            }
        }
        else
        {
            //error in problem type

            System.out.println("There has been an error in problem type selection!");
        }
    }
}
